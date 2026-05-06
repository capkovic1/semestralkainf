package gameManager;

import graphics.gameGraphics.GameGraphics;
import object.Canon;
import object.Wall;
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
    private final Set<Integer> pressedKeys = new HashSet<>();

    /**
     * Konštruktor nastaví referenciu na hernú grafiku pre interakciu s hrou.
     * @param game Inštancia triedy GameGraphics reprezentujúca hru.
     */
    public GameInputHandler(GameGraphics game) {
        this.game = game;
    }

    /**
     * Metóda volaná pravidelne na spracovanie kontinuálnych akcií, napríklad pohybu hráča.
     */
    public void tick() {
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
     * @param e informácie o udalosti myši (pozícia kliknutia)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.game.rotatePlayerToMouse(e.getX(), e.getY());

        int gridX = e.getX() / 20;
        int gridY = e.getY() / 20;

        switch (this.game.getInventory().getSelectedSlot()) {
            case 4:
                if (this.game.getPlayer().getStone() >= 50) {
                    this.game.getPlayer().addResource(ResourceType.STONE,-50);
                    this.game.getObjectManager().addWall(new Wall(gridX, gridY));
                } else {
                    this.game.getMessageDisplay().showMessage("Not enough stone! (Need 50)");
                }
                break;

            case 5:
                if (this.game.getPlayer().getStone() >= 150) {
                    if (this.game.getObjectManager().canPlaceCanon(gridX, gridY, this.game.getPlayer())) {
                        this.game.getPlayer().addResource(ResourceType.STONE,-150);
                        this.game.getObjectManager().addCanon(new Canon(gridX, gridY, 100));
                    } else {
                        this.game.getMessageDisplay().showMessage("Cannot place canon here!");
                    }
                } else {
                    this.game.getMessageDisplay().showMessage("Not enough stone! (Need 150)");
                }
                break;
        }
        this.game.repaint();
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
        } else if (this.pressedKeys.contains(KeyEvent.VK_SPACE)) {
            this.game.useWeapon();
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
        int cost = this.game.getPlayer().getWeapon().getLevel() * 200;
        if (this.game.getPlayer().getGold() >= cost) {
            boolean upgraded = this.game.getPlayer().getWeapon().upgrade();
            if (upgraded) {
                this.game.getPlayer().addResource(ResourceType.GOLD,-cost);
                this.game.getMessageDisplay().showMessage("Weapon succesfully upgraded!");
            } else {
                this.game.getMessageDisplay().showMessage("Weapon already upgraded!");
            }
        } else {
            this.game.getMessageDisplay().showMessage("Not enough gold! (Need " + cost + ")");
        }
    }

    /**
     * Aktualizuje smer pohybu hráča podľa stlačených kláves WASD alebo šípok.
     * Nastavuje smer pohybu v hernej grafike.
     */
    private void updateDirection() {
        int step = this.game.getPlayer().getSpeed();

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
        this.game.setLastDirection(dx, dy);
    }

    /**
     * Spracuje pohyb hráča podľa nastaveného smeru, ak hráč nie je mŕtvy
     * a je možné sa pohybovať na danú pozíciu.
     */
    private void handleMovement() {
        if (!this.game.getPlayer().isDead()) {
            Point dir = this.game.getLastDirection();
            int newX = this.game.getPlayer().getX() + dir.x;
            int newY = this.game.getPlayer().getY() + dir.y;

            if (this.game.getObjectManager().canMoveTo(newX, newY)) {
                this.game.getPlayer().setX(newX);
                this.game.getPlayer().setY(newY);
            }
        }
    }
}
