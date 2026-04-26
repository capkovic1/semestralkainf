package gameManager;

import entity.Player;
import gameManager.entityManager.*;

import java.awt.Graphics;

/**
 * Trieda GameManager je centrálny manažér pre hernú logiku,
 * ktorý koordinuje hráča, nepriateľov rôzneho typu, vlny nepriateľov a objekty hry.
 */
public class GameManager {

    private final Player player;

    private final UnifiedEnemyManager enemyManager;
    private WaveManager waveManager;
    private final ObjectManager objectManager;

    /**
     * Inicializuje hráča, manažérov nepriateľov, vlny a objekty.
     * @param player Inštancia hráča
     * @param cols počet stĺpcov mapy
     * @param rows počet riadkov mapy
     */
    public GameManager(Player player, int cols, int rows) {
        this.player = player;

        this.enemyManager = new UnifiedEnemyManager();

        this.waveManager = new WaveManager( this.enemyManager, cols, rows);
        this.objectManager = new ObjectManager();

        this.waveManager.startWaves();  // Spustí generovanie vĺn nepriateľov
        this.objectManager.generateObjects(7, 8, cols, rows);  // Vygeneruje objekty na mape
    }

    /**
     * Resetuje hru - zastaví vlny, vyčistí nepriateľov a objekty a znovu spustí vlny a generovanie objektov.
     */
    public void reset(int cols, int rows) {
        this.waveManager.stopWaves();

        this.enemyManager.clearEnemies();

        this.waveManager = new WaveManager(this.enemyManager, cols, rows);
        this.waveManager.startWaves();

        this.objectManager.getStones().clear();
        this.objectManager.getTrees().clear();
        this.objectManager.generateObjects(7, 8, cols, rows);
    }

    /**
     * Aktualizuje všetkých nepriateľov - volá ich update metódy.
     * @param g grafický kontext na kreslenie efektov
     */
    public void updateEntities(Graphics g) {
        this.enemyManager.updateEnemies(this.player , g);
    }

    /**
     * Vykreslí všetkých nepriateľov a objekty na obrazovku.
     * @param g grafický kontext na kreslenie
     */
    public void draw(Graphics g) {
        this.enemyManager.drawEnemies(g);
        this.objectManager.drawObjects(g);
    }

    /**
     * Aktualizuje objekty v hre, ako sú kanóny a ich strely.
     */
    public void updateObjects() {
        this.objectManager.updateCanons(null);
        this.objectManager.updateCanonBalls();
    }

    // Gettery pre prístup k manažérom a hráčovi
    public ObjectManager getObjectManager() {
        return this.objectManager;
    }

    public UnifiedEnemyManager getEnemyManager() {
        return this.enemyManager;
    }

    public Player getPlayer() {
        return this.player;
    }
}


