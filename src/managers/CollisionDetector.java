package managers;

import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;
import object.material.Material;
import object.structure.Structure;

import java.awt.*;
import java.util.ArrayList;


/**
 * Trieda zodpovedná za detekciu všetkých kolízií v hre.
 * Centralizuje všetky kontroly kolízií medzi entitami, projektilmi, štruktúrami a materiálmi.
 */
public class CollisionDetector {

    private CollisionDetector() {
    }


    /**
     * Skontroluje či sa hráč môže pohybovať na danú pozíciu.
     */
    public static boolean checkPlayerCanMoveTo(int x, int y, ArrayList<Structure> structures, ArrayList<Material> materials) {
        if (structures == null) structures = new ArrayList<>();
        if (materials == null) materials = new ArrayList<>();
        
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
     * Skontroluje či sa dá stavba postaviť na danú pozíciu.
     */
    public static boolean checkCanPlaceStructure(int x, int y, Player player, ArrayList<Structure> structures, ArrayList<Material> materials) {
        if (player == null) return false;
        if (structures == null) structures = new ArrayList<>();
        if (materials == null) materials = new ArrayList<>();
        
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
     * Vráti materiál ktorý je v dosahu zbrane hráča.
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
