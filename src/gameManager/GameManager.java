package gameManager;

import entity.Player;
import gameManager.entityManager.*;
import object.material.Material;
import resource.ResourceType;

import java.awt.*;

/**
 * Trieda GameManager je centrálny manažér pre hernú logiku,
 * ktorý koordinuje hráča, nepriateľov rôzneho typu, vlny nepriateľov a objekty hry.
 */
public class GameManager {

    private final Player player;

    private final EnemyManager enemyManager;
    private WaveManager waveManager;
    private final ObjectManager objectManager;
    private  EffectsManager effectsManager;

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

    public void handleHit() {

        Material material = this.objectManager.canHit(this.player.getX(), this.player.getY(), this.player);

        if (material != null) {
            ResourceType resourceType = material.changeHpBy(-this.player.getWeapon().getDmgToStructures());
            this.player.addResource(resourceType, this.player.getEfficiency() * this.player.getWeapon().getDmgToStructures());

            if (material.isDestroyed()) {
                this.objectManager.removeDestroyedMaterial(this.player);
            }
        }

        Rectangle attackArea = new Rectangle(
                this.player.getX() - this.player.getWeapon().getRange(),
                this.player.getY() - this.player.getWeapon().getRange(),
                50 + 2 * this.player.getWeapon().getRange(),
                50 + 2 * this.player.getWeapon().getRange()
        );

        for (entity.Enemy enemy : this.enemyManager.getEnemyList()) {
            Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 50, 50);
            if (attackArea.intersects(enemyRect)) {
                int damage = (player.getWeapon().getDamage() * player.getDamage());
                enemy.decreaseHp(damage);
                this.enemyManager.addDamageIndicator(enemy.getX() + 25, enemy.getY(), damage);
            }
        }

    }

    /**
     * Aktualizuje objekty v hre, ako sú kanóny a ich strely.
     */
    public void updateObjects() {
        this.objectManager.updateCanons(this.enemyManager.getEnemyList(), this.enemyManager);
    }
    public void updateEffects() {
        this.effectsManager.update(this.player);
    }

    // Gettery pre prístup k manažérom a hráčovi
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


