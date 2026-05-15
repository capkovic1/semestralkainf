package managers;


import entities.enemies.Enemy;
import entities.player.Player;
import object.material.Material;
import object.structure.Structure;
import resource.ResourceType;

import java.awt.Rectangle;

import java.util.ArrayList;


/**
 * Trieda zodpovedná za detekciu všetkých kolízií v hre.
 * Centralizuje kontroly kolízií medzi hráčom, nepriateľmi, objektmi a stavbami.
 */
public class CollisionDetector {

    private CollisionDetector() {
    }

    /**
     * Spracuje zásah hráča zbraňou na materiál a nepriateľov v dosahu.
     *
     * @param objectManager manažér objektov
     * @param player hráč, ktorý útočí
     * @param enemyManager manažér nepriateľov
     */
    public static void handleWeaponHit(ObjectManager objectManager, Player player, EnemyManager enemyManager) {

        Material material = objectManager.getHitableMaterial( player);

        if (material != null) {
            ResourceType resourceType = material.changeHpBy(-player.getWeapon().getDmgToStructures());
            player.addResource(resourceType, player.getEfficiency() * player.getWeapon().getDmgToStructures());

            if (material.isDestroyed()) {
                objectManager.removeDestroyedMaterial(player);
            }
        }

        CollisionDetector.handleHitToEnemy(player , enemyManager);

    }

    /**
     * Spracuje zásah nepriateľov v dosahu hráčovej zbrane.
     *
     * @param player hráč, ktorý útočí
     * @param enemyManager manažér nepriateľov
     */
    public static void handleHitToEnemy(Player player , EnemyManager enemyManager) {
        ArrayList<Enemy> hitEnemies = getEnemiesInRange(player.getX(), player.getY(), player.getWeapon().getRange(), enemyManager.getEnemyList());

        for (Enemy enemy : hitEnemies) {
            int damage = (player.getWeapon().getDamage() * player.getDamage());
            enemy.takeDamage(damage);
            enemyManager.addDamageIndicator(enemy, damage);
        }
    }

    /**
     * Skontroluje, či sa hráč zrazil s nepriateľom.
     *
     * @param player hráč na kontrolu
     * @param enemy nepriateľ na kontrolu
     * @return {@code true}, ak sa ich obdĺžniky prekrývajú, inak {@code false}
     */
    public static boolean checkPlayerCollidesWithEnemy(Player player, Enemy enemy) {
        if (player == null || enemy == null) {
            return false;
        }
        Rectangle playerRect = new Rectangle(player.getX(), player.getY(), 50, 50);
        Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 50, 50);
        return playerRect.intersects(enemyRect);

    }
    /**
     * Skontroluje, či sa hráč môže pohybovať na danú pozíciu.
     *
     * @param x nová X súradnica hráča
     * @param y nová Y súradnica hráča
     * @param structures zoznam stavieb na mape
     * @param materials zoznam materiálov na mape
     * @return {@code true}, ak sa hráč môže pohybovať, inak {@code false}
     */
    public static boolean checkPlayerCanMoveTo(int x, int y, ArrayList<Structure> structures, ArrayList<Material> materials) {
        if (structures == null) {
            structures = new ArrayList<>();
        }
        if (materials == null) {
            materials = new ArrayList<>();
        }
        
        int playerSize = 50;
        Rectangle playerBounds = new Rectangle(x, y, playerSize, playerSize);

        for (int i = 0; i < playerSize; i += 10) {
            for (int j = 0; j < playerSize; j += 10) {
                int checkX = (x + i) / 20;
                int checkY = (y + j) / 20;

                for (Structure structure : structures) {
                    if (structure != null && structure.getX() == checkX && structure.getY() == checkY) {
                        return false;
                    }
                }
            }
        }

        for (Material material : materials) {
            if (material != null && playerBounds.intersects(material.getBounds())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Skontroluje, či sa dá stavba postaviť na danú pozíciu.
     *
     * @param x X súradnica na mriežke
     * @param y Y súradnica na mriežke
     * @param player hráč, ktorý stavia
     * @param structures zoznam existujúcich stavieb
     * @param materials zoznam materiálov na mape
     * @return {@code true}, ak je možné stavbu umiestniť, inak {@code false}
     */
    public static boolean checkCanPlaceStructure(int x, int y, Player player, ArrayList<Structure> structures, ArrayList<Material> materials) {
        if (player == null) {
            return false;
        }
        if (structures == null) {
            structures = new ArrayList<>();
        }
        if (materials == null) {
            materials = new ArrayList<>();
        }
        
        Rectangle structureArea = new Rectangle(x * 20, y * 20, 40, 40);

        if (new Rectangle(player.getX(), player.getY(), 50, 50).intersects(structureArea)) {
            return false;
        }
        for (Structure structure : structures) {
            if (structure != null && new Rectangle(structure.getX() * 20, structure.getY() * 20, 20, 20).intersects(structureArea)) {
                return false;
            }
        }

        for (Material material : materials) {
            if (material != null && material.getBounds().intersects(structureArea)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Vráti materiál, ktorý je v dosahu zbrane hráča.
     *
     * @param playerX X súradnica hráča
     * @param playerY Y súradnica hráča
     * @param range dosah zbrane
     * @param materials zoznam materiálov na kontrolu
     * @return materiál v dosahu alebo {@code null}, ak žiadny nie je
     */
    public static Material getHitMaterial(int playerX, int playerY, int range, ArrayList<Material> materials) {
        if (materials == null) {
            return null;
        }
        
        int playerSize = 50;
        Rectangle hitBox = new Rectangle(playerX - range, playerY - range, playerSize + 2 * range, playerSize + 2 * range);

        for (Material material : materials) {
            if (material != null && hitBox.intersects(material.getBounds())) {
                return material;
            }
        }
        return null;
    }

    /**
     * Vráti všetkých nepriateľov v dosahu zbrane.
     *
     * @param playerX X súradnica hráča
     * @param playerY Y súradnica hráča
     * @param range dosah zbrane
     * @param enemies zoznam nepriateľov na kontrolu
     * @return zoznam nepriateľov v dosahu, prípadne prázdny zoznam
     */
    public static ArrayList<Enemy> getEnemiesInRange(int playerX, int playerY, int range, ArrayList<Enemy> enemies) {
        ArrayList<Enemy> hitEnemies = new ArrayList<>();
        
        if (enemies == null) {
            return hitEnemies;
        }
        
        Rectangle attackArea = new Rectangle(playerX - range, playerY - range, 50 + 2 * range, 50 + 2 * range);

        for (Enemy enemy : enemies) {
            if (enemy != null) {
                Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 50, 50);
                if (attackArea.intersects(enemyRect)) {
                    hitEnemies.add(enemy);
                }
            }
        }
        return hitEnemies;
    }
}
