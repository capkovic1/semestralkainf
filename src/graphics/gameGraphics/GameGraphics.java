package graphics.gameGraphics;

import entity.Player;

import gameManager.GameManager;
import gameManager.GameHistory;
import gameManager.GameInputHandler;
import gameManager.ObjectManager;
import graphics.entityGraphics.PlayerGraphics;
import graphics.infoGraphics.InventoryGraphics;
import graphics.infoGraphics.PlayerInfoGraphics;
import graphics.ruka.HammerGraphics;
import resource.ResourceType;
import weapon.Hammer;
import object.Material;
import object.Stone;
import object.Wood;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Timer;
/**
 * Trieda zodpovedná za vykresľovanie hlavného herného okna a správu hernej logiky
 * týkajúcej sa vykresľovania, spracovania vstupu, zobrazovania správ, inventára a riadenia
 * pohybu hráča.
 * Využíva aj GameManager pre logiku hry, GameInputHandler pre spracovanie vstupov a obsahuje
 * rôzne podtriedy grafiky ako PlayerGraphics, BoardGraphics a InventoryGraphics.
 */
public class GameGraphics extends JPanel {

    private long startTime;
    private String message = "";
    private long messageDisplayTime = 0;
    private static final long MESSAGEDURATION = 2000;

    private int lastDirectionX = 0;
    private int lastDirectionY = 0;

    private Timer movementTimer;
    private final BoardGraphics boardGraphics;

    private PlayerGraphics playerGraphics;

    private InventoryGraphics inventoryGraphics;

    private final GameManager gameManager;


    private GameInputHandler inputHandler;

    private final int rows;
    private final int cols;

    private long lastRegenTime = System.currentTimeMillis();
    private long objectTimer = System.currentTimeMillis();

    private final PlayerInfoGraphics playerInfoGraphics;
    /**
     * Konštruktor vytvára hernú grafiku pre daného hráča, nastavuje veľkosť hernej mriežky
     * podľa veľkosti obrazovky, inicializuje grafiku hernej dosky, hráča, inventára a spracovanie vstupov.
     * Spúšťa herný timer na aktualizáciu hry.
     *
     * @param player Inštancia hráča, pre ktorého sa bude vykresľovať a spravovať hra.
     */
    public GameGraphics(Player player) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.rows = screenSize.height / 30;
        this.cols = screenSize.width / 30;

        this.boardGraphics = new BoardGraphics(this.rows, this.cols);

        this.playerGraphics = new PlayerGraphics(player, new HammerGraphics(this::repaint));

        this.gameManager = new GameManager(player , this.cols , this.rows);
        this.playerInfoGraphics = new PlayerInfoGraphics(player);
        this.startTime = System.currentTimeMillis();

        this.inventoryGraphics = new InventoryGraphics(player , this.playerGraphics);

        this.inputHandler = new GameInputHandler(this);
        this.setFocusable(true);
        this.addKeyListener(this.inputHandler);
        this.addMouseListener(this.inputHandler);
        this.addMouseListener(this.inputHandler);

        this.movementTimer = new Timer(20, e -> {
            this.inputHandler.tick();

            if (this.gameManager.getPlayer().getHealth() <= 0) {
                this.movementTimer.stop();
                GameHistory.saveHistory(this.gameManager.getPlayer().getScore(), this.startTime);
                this.showDeadMenu();
                return;
            }


            long currentTime = System.currentTimeMillis();
            if (currentTime - this.lastRegenTime >= 1000) {
                this.regeneratePlayer();
                this.lastRegenTime = currentTime;
            }

            if (currentTime - this.objectTimer >= 30000) {
                this.gameManager.getObjectManager().generateObjects(1, 1 , this.cols , this.rows);
                this.objectTimer = currentTime;
            }

            this.gameManager.updateObjects();

            this.gameManager.updateEntities(this.getGraphics());

            this.repaint();
        });
        this.movementTimer.start();
    }

    /**
     * Nastaví uhol natočenia hráča tak, aby smeroval k pozícii myši.
     * Následne sa prekreslí herná plocha.
     *
     * @param mouseX X súradnica myši v pixeloch.
     * @param mouseY Y súradnica myši v pixeloch.
     */
    public void rotatePlayerToMouse(int mouseX, int mouseY) {
        int playerPixelX = this.gameManager.getPlayer().getX();
        int playerPixelY = this.gameManager.getPlayer().getY();
        double angle = Math.atan2(mouseY - playerPixelY, mouseX - playerPixelX);
        this.gameManager.getPlayer().setAngle(Math.toDegrees(angle));
        this.repaint();
    }

    /**
     * Metóda na vykreslenie všetkých prvkov hry: hernej dosky, hráča, entít, informácií o hráčovi,
     * inventára a dočasnej správy.
     *
     * @param g Grafický kontext, do ktorého sa kreslí.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.boardGraphics.paintComponent(g);
        this.playerGraphics.getPlayerGrafic(g);
        this.gameManager.draw(g);

        this.playerInfoGraphics.drawPlayerResources(g, this.getWidth(), this.getHeight() , this.startTime);

        this.inventoryGraphics.draw(g);

        if (System.currentTimeMillis() < this.messageDisplayTime) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g.getFontMetrics();
            int x = (this.getWidth() - fm.stringWidth(this.message)) / 2;
            int y = this.getHeight() / 2;

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(x - 10, y - fm.getHeight(), fm.stringWidth(this.message) + 20, fm.getHeight() + 10);

            g.setColor(Color.WHITE);
            g.drawString(this.message, x, y);
        }
    }

    /**
     * Vnútorná logika spracovania zásahu materiálu (kameň, drevo) alebo nepriateľov
     * podľa aktuálnej zbrane hráča.
     * Demonštruje polymorfizmus - všetci nepriatelia sú spracovaní cez rozhraní Enemy.
     */
    private void handleMaterialHit() {
        Player player = this.gameManager.getPlayer();

        Material material = this.gameManager.getObjectManager().canHit(player.getX(), player.getY(), player);

        if (material instanceof Stone stone) {
            stone.changeHpBy(-player.getWeapon().getDmgToStructures());
            player.addResource(ResourceType.STONE,3 * player.getWeapon().getDmgToStructures());

            if (stone.isDestroyed()) {
                this.gameManager.getObjectManager().removeDestroyedStones(player);
            }

        } else if (material instanceof Wood tree) {
            tree.changeHpBy(-player.getWeapon().getDmgToStructures());
            player.addResource(ResourceType.WOOD,3 * player.getWeapon().getDmgToStructures());

            if (tree.isDestroyed()) {
                this.gameManager.getObjectManager().removeDestroyedTrees(player);
            }
        } else {
            Rectangle attackArea = new Rectangle(
                    player.getX() - player.getWeapon().getRange(),
                    player.getY() - player.getWeapon().getRange(),
                    50 + 2 * player.getWeapon().getRange(),
                    50 + 2 * player.getWeapon().getRange()
            );

            // POLYMORFIZMUS - všetci nepriatelia sú v jednom ArrayList<Enemy>
            for (entity.Enemy enemy : this.gameManager.getEnemyManager().getEnemyList()) {
                Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 50, 50);
                if (attackArea.intersects(enemyRect)) {
                    enemy.decreaseHp(player.getWeapon().getDamage());
                }
            }

        }
    }

    /**
     * Zobrazí dočasnú správu uprostred herného okna, ktorá zmizne po krátkom čase.
     *
     * @param message Text správy, ktorá sa má zobraziť.
     */
    public void showMessage(String message) {
        this.message = message;
        this.messageDisplayTime = System.currentTimeMillis() + MESSAGEDURATION;
        this.repaint();
    }
    /**
     * Získava správcu objektov hry.
     *
     * @return Objekt ObjectManager riadiaci herné objekty.
     */
    public ObjectManager getObjectManager() {
        return this.gameManager.getObjectManager();
    }
    /**
     * Získava hráča z hry.
     *
     * @return Inštancia hráča.
     */
    public Player getPlayer() {
        return this.gameManager.getPlayer();
    }
    /**
     * Nastaví hráčovu zbraň na kladivo.
     */
    public void setWeaponHammer() {
        this.playerGraphics.setHandGraphics(new HammerGraphics(this::repaint));
        this.gameManager.getPlayer().setWeapon(new Hammer(30, 25));
    }
    /**
     * Použije aktuálnu zbraň hráča a spracuje zásah na materiály alebo nepriateľov.
     */
    public void useWeapon() {
        this.playerGraphics.getHandGraphics().use();
        this.handleMaterialHit();
    }
    /**
     * Nastaví posledný smer pohybu hráča.
     *
     * @param x Smer po osi X.
     * @param y Smer po osi Y.
     */
    public void setLastDirection(int x, int y) {
        this.lastDirectionX = x;
        this.lastDirectionY = y;
    }
    /**
     * Získava posledný smer pohybu hráča.
     *
     * @return Objekt Point reprezentujúci posledný smer pohybu.
     */
    public Point getLastDirection() {
        return new Point(this.lastDirectionX, this.lastDirectionY);
    }
    /**
     * Získava grafiku inventára.
     *
     * @return Inštancia InventoryGraphics.
     */
    public InventoryGraphics getInventory() {
        return this.inventoryGraphics;
    }
    /**
     * Zobrazí panel "You Died" s možnosťami reštartu hry alebo zobrazenia hernej histórie.
     * Tento panel prekryje aktuálne herné okno.
     */
    public void showDeadMenu() {
        JPanel deadPanel = new JPanel();
        deadPanel.setLayout(new BoxLayout(deadPanel, BoxLayout.Y_AXIS));
        deadPanel.setBackground(new Color(0, 0, 0, 200));
        deadPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        JLabel deadLabel = new JLabel("You Died");
        deadLabel.setFont(new Font("Arial", Font.BOLD, 40));
        deadLabel.setForeground(Color.RED);
        deadLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> this.restartGame());

        JButton historyButton = new JButton("Show History");
        historyButton.setFont(new Font("Arial", Font.PLAIN, 24));
        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyButton.addActionListener(e -> GameHistory.showHistory(this));

        deadPanel.add(Box.createVerticalGlue());
        deadPanel.add(deadLabel);
        deadPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        deadPanel.add(restartButton);
        deadPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        deadPanel.add(historyButton);
        deadPanel.add(Box.createVerticalGlue());

        this.setLayout(null);
        this.add(deadPanel);
        this.setComponentZOrder(deadPanel, 0);
        this.revalidate();
        this.repaint();
    }
    /**
     * Reštartuje hru: resetuje stav hráča, herného manažéra, nastaví zbraň,
     * preinicializuje grafiku a spustí pohybový timer.
     */
    private void restartGame() {
        Player player = this.gameManager.getPlayer();

        if (this.movementTimer != null && this.movementTimer.isRunning()) {
            this.movementTimer.stop();
        }

        player.reset();
        player.setX(this.getWidth() / 2);
        player.setY(this.getHeight() / 2);
        player.setHealth(player.getMaxHealth());

        this.gameManager.reset(this.cols, this.rows);

        this.setWeaponHammer();

        this.playerGraphics = new PlayerGraphics(player, new HammerGraphics(this::repaint));
        this.inventoryGraphics = new InventoryGraphics(player, this.playerGraphics);

        this.removeAll();

        this.inputHandler = new GameInputHandler(this);
        this.setFocusable(true);
        this.addKeyListener(this.inputHandler);
        this.addMouseListener(this.inputHandler);


        this.startTime = System.currentTimeMillis();
        this.lastRegenTime = System.currentTimeMillis();

        this.revalidate();
        this.repaint();

        this.movementTimer = new Timer(20, e -> {
            this.inputHandler.tick();

            if (player.getHealth() <= 0) {
                this.movementTimer.stop();
                GameHistory.saveHistory(this.gameManager.getPlayer().getScore(), this.startTime);
                this.showDeadMenu();
                return;
            }
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.lastRegenTime >= 1000) {
                this.regeneratePlayer();
                this.lastRegenTime = currentTime;
            }

            if (currentTime - this.objectTimer >= 30000) {
                this.gameManager.getObjectManager().generateObjects(1, 1 , this.cols , this.rows);
                this.objectTimer = currentTime;
            }

            this.gameManager.updateObjects();
            this.gameManager.updateEntities(this.getGraphics());

            this.repaint();
        });

        this.movementTimer.start();
    }
    /**
     * Regeneruje zdravie hráča o 1, ak ešte nie je na maximálnej hodnote.
     */
    public void regeneratePlayer() {
        if (this.gameManager.getPlayer().getHealth() < this.gameManager.getPlayer().getMaxHealth()) {
            this.gameManager.getPlayer().setHealth(this.gameManager.getPlayer().getHealth() + 1);
        }

    }
}
