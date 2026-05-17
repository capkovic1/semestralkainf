package graphics.gameGraphics;

import entities.player.Player;

import managers.EnemyManager;
import managers.GameInputHandler;
import managers.GameLoop;
import managers.GameManager;
import managers.ObjectManager;
import managers.GameHistory;

import graphics.infoGraphics.ActiveEffectsGraphics;
import graphics.infoGraphics.InventoryGraphics;
import graphics.infoGraphics.PlayerInfoGraphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

/**
 * Trieda zodpovedná za vykresľovanie hlavného herného okna a správu hernej logiky
 * týkajúcej sa vykresľovania
 *
 */
public class GamePanel extends JPanel {

    private long startTime ;

    private final BoardGraphics boardGraphics;

    private InventoryGraphics inventoryGraphics;
    private final ActiveEffectsGraphics activeEffectsGraphics;

    private final GameManager gameManager;
    private final MessageDisplay messageDisplay;

    private GameInputHandler inputHandler;

    private final int rows;
    private final int cols;

    private final GameLoop gameLoop;

    private final PlayerInfoGraphics playerInfoGraphics;
    /**
     * Konštruktor vytvára hernú grafiku pre daného hráča, nastavuje veľkosť hernej mriežky
     * podľa veľkosti obrazovky, inicializuje grafiku hernej dosky, hráča, inventára a spracovanie vstupov.
     * Spúšťa herný timer na aktualizáciu hry.
     *
     * @param player Inštancia hráča, pre ktorého sa bude vykresľovať a spravovať hra.
     */
    public GamePanel(Player player) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.rows = screenSize.height / 30;
        this.cols = screenSize.width / 30;

        this.startTime = System.currentTimeMillis();

        this.boardGraphics = new BoardGraphics(this.rows, this.cols);
        this.gameManager = new GameManager(player , this.cols , this.rows);

        this.playerInfoGraphics = new PlayerInfoGraphics(player);
        this.inventoryGraphics = new InventoryGraphics(player);
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
     * Metóda na vykreslenie všetkých prvkov hry: hernej dosky, hráča, entít, informácií o hráčovi,
     * inventára a dočasnej správy.
     *
     * @param g Grafický kontext, do ktorého sa kreslí.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.boardGraphics.paintComponent(g);
        this.gameManager.getPlayer().getPlayerGraphics().draw(g);

        this.gameManager.getEnemyManager().drawEnemies(g);
        this.gameManager.getObjectManager().drawObjects(g);

        this.playerInfoGraphics.drawPlayerResources(g, this.getWidth(), this.getHeight() , this.startTime);
        this.activeEffectsGraphics.drawActiveEffects((Graphics2D)g , this.gameManager.getEffectsManager());
        this.inventoryGraphics.draw(g);

        this.messageDisplay.draw(g, this.getWidth(), this.getHeight());
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

    public EnemyManager getEnemyManager() {
        return this.gameManager.getEnemyManager();
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
        restartButton.addActionListener(_ -> this.restartGame());

        JButton historyButton = new JButton("Show History");
        historyButton.setFont(new Font("Arial", Font.PLAIN, 24));
        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyButton.addActionListener(_ -> GameHistory.showHistory(this));

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

        this.inventoryGraphics = new InventoryGraphics(player);

        this.removeAll();

        this.inputHandler = new GameInputHandler(this);
        this.setFocusable(true);
        this.addKeyListener(this.inputHandler);
        this.addMouseListener(this.inputHandler);

        this.revalidate();
        this.repaint();

        this.startTime = System.currentTimeMillis();
        if (this.gameLoop != null) {
            this.gameLoop.restart(this.inputHandler, this.startTime);
        }
    }

    public MessageDisplay getMessageDisplay() {
        return this.messageDisplay;
    }
}