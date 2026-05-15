package managers;

import entities.player.Player;

/**
 * Trieda GameManager je centrálny manažér pre hernú logiku,
 * ktorý koordinuje hráča, nepriateľov rôzneho typu, vlny nepriateľov a objekty hry.
 */
public class GameManager {

    private final Player player;

    private final EnemyManager enemyManager;
    private WaveManager waveManager;
    private final ObjectManager objectManager;
    private final EffectsManager effectsManager;

    /**
     * Inicializuje hráča, manažérov nepriateľov, vlny a objekty.
     * @param player Inštancia hráča
     * @param cols počet stĺpcov mapy
     * @param rows počet riadkov mapy
     */
    public GameManager(Player player, int cols, int rows) {
        this.player = player;
        this.effectsManager = new EffectsManager();

        this.enemyManager = new EnemyManager();

        this.waveManager = new WaveManager( this.enemyManager, cols, rows);
        this.objectManager = new ObjectManager();

        this.waveManager.startWaves();
        this.objectManager.generateObjects(7, 8, cols, rows);
    }

    /**
     * Resetuje hru - zastaví vlny, vyčistí nepriateľov a objekty a znovu spustí vlny a generovanie objektov.
     */
    public void reset(int cols, int rows) {
        this.waveManager.stopWaves();

        this.effectsManager.clearEffects();
        this.enemyManager.clearEnemies();

        this.waveManager = new WaveManager(this.enemyManager, cols, rows);
        this.waveManager.startWaves();

        this.objectManager.reset();
        this.objectManager.generateObjects(7, 8, cols, rows);
    }

    public boolean updateEntities() {
        return  this.enemyManager.updateEnemies(this.player);
    }

    /**
     * Aktualizuje objekty v hre, ako sú kanóny a ich strely.
     */
    public void updateObjects() {
        this.objectManager.updateStructures(this.enemyManager.getEnemyList(), this.enemyManager , this.player);
    }
    public void updateEffects() {
        this.effectsManager.update(this.player);
    }

    public ObjectManager getObjectManager() {
        return this.objectManager;
    }

    public EnemyManager getEnemyManager() {
        return this.enemyManager;
    }

    public EffectsManager getEffectsManager() {
        return this.effectsManager;
    }

    public Player getPlayer() {
        return this.player;
    }


}


