package graphics.gameGraphics;

import entity.Player;

import gameManager.*;
import graphics.entityGraphics.PlayerGraphics;
import graphics.infoGraphics.ActiveEffectsGraphics;
import graphics.infoGraphics.InventoryGraphics;
import graphics.infoGraphics.PlayerInfoGraphics;
import graphics.ruka.HammerGraphics;
import weapon.Hammer;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

/**
 * Trieda zodpovedná za vykresľovanie hlavného herného okna a správu hernej logiky
 * týkajúcej sa vykresľovania, spracovania vstupu, zobrazovania správ, inventára a riadenia
 * pohybu hráča.
 * Využíva aj GameManager pre logiku hry, GameInputHandler pre spracovanie vstupov a obsahuje
 * rôzne podtriedy grafiky ako PlayerGraphics, BoardGraphics a InventoryGraphics.
 */
public class GameGraphics extends JPanel {

    private long startTime ;

    private int lastDirectionX = 0;
    private int lastDirectionY = 0;

    private final BoardGraphics boardGraphics;

    private PlayerGraphics playerGraphics;

    private InventoryGraphics inventoryGraphics;
    private ActiveEffectsGraphics activeEffectsGraphics;

    private final GameManager gameManager;
    private final MessageDisplay messageDisplay;

    private GameInputHandler inputHandler;

    private final int rows;
    private final int cols;

    private GameLoop gameLoop;

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

        this.startTime = System.currentTimeMillis();

        this.boardGraphics = new BoardGraphics(this.rows, this.cols);
        this.playerGraphics = new PlayerGraphics(player, new HammerGraphics(this::repaint));
        this.gameManager = new GameManager(player , this.cols , this.rows);

        this.playerInfoGraphics = new PlayerInfoGraphics(player);
        this.inventoryGraphics = new InventoryGraphics(player , this.playerGraphics);
        this.activeEffectsGraphics = new ActiveEffectsGraphics();
        this.messageDisplay = new MessageDisplay();

        this.inputHandler = new GameInputHandler(this);
        this.setFocusable(true);
        this.addKeyListener(this.inputHandler);
        this.addMouseListener(this.inputHandler);
        this.addMouseListener(this.inputHandler);

            this.gameLoop = new GameLoop(this.gameManager, this.inputHandler, this::repaint, this::showDeadMenu, this.startTime , this.rows , this.cols);
            this.gameLoop.start();
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.boardGraphics.paintComponent(g);
        this.playerGraphics.getPlayerGrafic(g);

        this.gameManager.getEnemyManager().drawEnemies(g);
        this.gameManager.getObjectManager().drawObjects(g);

        this.playerInfoGraphics.drawPlayerResources(g, this.getWidth(), this.getHeight() , this.startTime);
        this.activeEffectsGraphics.drawActiveEffects((Graphics2D) g , this.gameManager.getEffectsManager());
        this.inventoryGraphics.draw(g);
        
        this.messageDisplay.draw(g,this.getWidth(),this.getHeight());
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
        this.gameManager.handleHit();
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

        if (this.gameLoop != null) {
            this.gameLoop.stop();
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

        this.revalidate();
        this.repaint();

        this.startTime = System.currentTimeMillis();
        this.gameLoop.restart(this.inputHandler, this.startTime);

    }

    public MessageDisplay getMessageDisplay() {
        return this.messageDisplay;
    }
}
