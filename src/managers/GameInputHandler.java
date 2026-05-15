package managers;

import entities.player.Player;
import graphics.gameGraphics.GameGraphics;
import object.structure.Canon;
import object.structure.Structure;
import object.structure.Wall;
import resource.ResourceType;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * Trieda GameInputHandler spracováva vstupy od používateľa (klávesnica a myš)
 * a podľa nich ovláda pohyb hráča, rotáciu, výber inventára, stav zbraní a
 * stavať objekty ako stenu alebo kanón v hre.
 */
public class GameInputHandler extends MouseAdapter implements KeyListener {

    private final GameGraphics game;
    private final Player player;

    private final Set<Integer> pressedKeys = new HashSet<>();
    private int lastDirectionX ;
    private int lastDirectionY;

    private long lastClickTime = 0;
    private static final long CLICK_DEBOUNCE_MS = 200;

    /**
     * Konštruktor nastaví referenciu na hernú grafiku pre interakciu s hrou.
     *
     * @param game Inštancia triedy GameGraphics reprezentujúca hru.
     */
    public GameInputHandler(GameGraphics game) {
        this.player = game.getPlayer();
        this.game = game;
    }

    /**
     * Metóda volaná pravidelne na spracovanie kontinuálnych akcií, napríklad pohybu hráča.
     */
    public void update() {
        this.handleMovement();
    }

    /**
     * Metóda spracuje stlačenie klávesu - uloží ho do setu a aktualizuje smer a stav hráča.
     * @param e informácie o stlačenom klávese
     */
    @Override
    public void keyPressed(KeyEvent e) {
        this.pressedKeys.add(e.getKeyCode());
        this.updateDirection();
        this.updatePlayerState();
    }

    /**
     * Metóda spracuje uvoľnenie klávesu - odstráni ho zo setu a aktualizuje smer hráča.
     * @param e informácie o uvoľnenom klávese
     */
    @Override
    public void keyReleased(KeyEvent e) {
        this.pressedKeys.remove(e.getKeyCode());
        this.updateDirection();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     * Metóda spracuje uvoľnenie tlačidla myši - rotuje hráča smerom k myši a
     * umožňuje stavať objekty (stenu, kanón) podľa vybranej položky v inventári a dostupných zdrojov.
     * Debouncing zabezpečuje, že sa stavba skúša iba raz za {@code CLICK_DEBOUNCE_MS} milisekúnd.
     * @param e informácie o udalosti myši (pozícia kliknutia)
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        // Debouncing - ignoruj kliknutia, ktoré prichádzajú príliš rýchlo za sebou
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastClickTime < CLICK_DEBOUNCE_MS) {
            return;
        }
        this.lastClickTime = currentTime;

        this.rotatePlayerToMouse(e.getX(), e.getY());

        int gridX = e.getX() / 20;
        int gridY = e.getY() / 20;

        switch (this.game.getInventory().getSelectedSlot()) {
            case 4:
                this.handleBuilding("wall", new Wall(gridX, gridY), gridX, gridY);
                break;

            case 5:
                this.handleBuilding("canon", new Canon(gridX, gridY , 100), gridX, gridY);
                break;
        }
        this.game.repaint();
    }

    /**
     * Pokúsi sa postaviť stavbu (wall/canon) ak má hráč dostatok surovín.
     *
     * @param name názov stavby
     * @param structure inštancia stavby
     * @param gridX x súradnica na mriežke
     * @param gridY y súradnica na mriežke
     */
    public void handleBuilding(String name  , Structure structure , int gridX , int gridY) {
        if (this.player.getStone() >= structure.getPrice()) {
            if (this.game.getObjectManager().canPlaceStructure(gridX, gridY, this.game.getPlayer())) {
                this.player.addResource(ResourceType.STONE, -structure.getPrice());
                this.game.getObjectManager().addStructure(structure);
            } else {
                this.game.getMessageDisplay().showMessage("Cannot place " + name + " here!");
            }
        } else {
            this.game.getMessageDisplay().showMessage("Not enough stone! (Need " + structure.getPrice() + ")");
        }
    }
    /**
     * Aktualizuje vybraný slot inventára podľa stlačených kláves (1-6),
     * spúšťa použitie zbrane (space) a upgrade zbrane (U).
     * Tiež spracováva pohyb a prekresľuje herné okno.
     */
    private void updatePlayerState() {
        if (this.pressedKeys.contains(KeyEvent.VK_1)) {
            this.game.getInventory().selectSlot(0);
        } else if (this.pressedKeys.contains(KeyEvent.VK_2)) {
            this.game.getInventory().selectSlot(1);
        } else if (this.pressedKeys.contains(KeyEvent.VK_3)) {
            this.game.getInventory().selectSlot(2);
        } else if (this.pressedKeys.contains(KeyEvent.VK_4)) {
            this.game.getInventory().selectSlot(3);
        } else if (this.pressedKeys.contains(KeyEvent.VK_5)) {
            this.game.getInventory().selectSlot(4);
        } else if (this.pressedKeys.contains(KeyEvent.VK_6)) {
            this.game.getInventory().selectSlot(5);
        }
        if (this.pressedKeys.contains(KeyEvent.VK_SPACE)) {
            this.useWeapon();
        }
        if (this.pressedKeys.contains(KeyEvent.VK_U)) {
            this.upgradeWeapon();
        }

        this.handleMovement();
        this.game.repaint();
    }

    /**
     * Pokúsi sa vylepšiť aktuálnu zbraň hráča, ak má hráč dosť zlata.
     * Pri úspechu odpočíta cenu z hráčovho zlata a zobrazí správu.
     * Pri neúspechu (napr. maximálny level) zobrazí príslušnú správu.
     */
    private void upgradeWeapon() {
        int cost = this.player.getWeapon().getLevel() * 200;
        if (this.player.getGold() >= cost) {
            boolean upgraded = this.player.getWeapon().upgrade();
            if (upgraded) {
                this.player.addResource(ResourceType.GOLD, -cost);
                this.game.getMessageDisplay().showMessage("Weapon succesfully upgraded!");
            } else {
                this.game.getMessageDisplay().showMessage("Weapon already upgraded!");
            }
        } else {
            this.game.getMessageDisplay().showMessage("Not enough gold! (Need " + cost + ")");
        }
    }
    /**
     * Použije aktuálnu zbraň hráča, spustí animáciu a detekciu zásahu.
     */
    private void useWeapon() {
        this.player.getPlayerGraphics().getHandGraphics().use();
        CollisionDetector.handleWeaponHit(this.game.getObjectManager(),  this.player, this.game.getEnemyManager());
    }

    /**
     * Nastaví uhol natočenia hráča tak, aby smeroval k pozícii myši.
     * Následne sa prekreslí herná plocha.
     *
     * @param mouseX X súradnica myši v pixeloch.
     * @param mouseY Y súradnica myši v pixeloch.
     */
    public void rotatePlayerToMouse(int mouseX, int mouseY) {
        int playerPixelX = this.player.getX();
        int playerPixelY = this.player.getY();
        double angle = Math.atan2(mouseY - playerPixelY, mouseX - playerPixelX);
        this.player.setAngle(Math.toDegrees(angle));
        this.game.repaint();
    }
    /**
     * Aktualizuje smer pohybu hráča podľa stlačených kláves WASD alebo šípok.
     * Nastavuje smer pohybu v hernej grafike.
     */
    private void updateDirection() {
        int step = this.player.getSpeed();

        int dx = 0;
        int dy = 0;

        if (this.pressedKeys.contains(KeyEvent.VK_W) || this.pressedKeys.contains(KeyEvent.VK_UP)) {
            dy -= step;
        }
        if (this.pressedKeys.contains(KeyEvent.VK_S) || this.pressedKeys.contains(KeyEvent.VK_DOWN)) {
            dy += step;
        }
        if (this.pressedKeys.contains(KeyEvent.VK_A) || this.pressedKeys.contains(KeyEvent.VK_LEFT)) {
            dx -= step;
        }
        if (this.pressedKeys.contains(KeyEvent.VK_D) || this.pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            dx += step;
        }
        this.setLastDirection(dx, dy);
    }
    private void setLastDirection(int x, int y) {
        this.lastDirectionX = x;
        this.lastDirectionY = y;
    }
    private Point getLastDirection() {
        return new Point(this.lastDirectionX, this.lastDirectionY);
    }
    /**
     * Spracuje pohyb hráča podľa nastaveného smeru, ak hráč nie je mŕtvy
     * a je možné sa pohybovať na danú pozíciu.
     */
    private void handleMovement() {
        if (!this.player.isDead()) {
            Point dir = this.getLastDirection();
            int newX = this.player.getX() + dir.x;
            int newY = this.player.getY() + dir.y;

            if (this.game.getObjectManager().canMoveTo(newX, newY)) {
                this.player.setX(newX);
                this.player.setY(newY);
            }
        }
    }
}
