package managers;

import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;
import projectile.Projectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Trieda zodpovedná za správu všetkých projektilov v hre bez ohľadu na ich zdroj.
 * Aktualizuje pozície, počíta kolízie s nepriateľmi a odstraňuje neplatné projektily.
 */
public class ProjectileManager {
    private static ProjectileManager instance;
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    /**
     * Pridá nový projektil do správy.
     *
     * @param projectile Projektil, ktorý sa má spravovať
     */
    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }
    public static ProjectileManager getInstance() {
        if (instance == null) {
            instance = new ProjectileManager();
        }
        return instance;
    }
    /**
     * Aktualizuje všetky projektily - pohyb, kolízie s nepriateľmi, odstránenie.
     *
     * @param enemies Zoznam všetkých nepriateľov v hre
     * @param enemyManager EnemyManager pre pridávanie damage indikátorov
     */
    public void updateProjectiles(ArrayList<Enemy> enemies, EnemyManager enemyManager , Player player) {
        Iterator<Projectile> projIter = this.projectiles.iterator();

        while (projIter.hasNext()) {
            Projectile proj = projIter.next();
            proj.update();

            boolean hit = false;
            for (Enemy enemy : enemies) {
                if (this.checkProjectileCollision(proj, enemy) ) {
                    enemyManager.addDamageIndicator(enemy, proj.getDamage());
                    hit = true;
                    break;
                }
            }
            if (this.checkProjectileCollision(proj, player)) {
                enemyManager.addDamageIndicator(player, proj.getDamage());
                hit = true;
            }
            if (hit || proj.shouldRemove()) {
                projIter.remove();
            }
        }
    }

    /**
     * Skontroluje či sa projektil zrazil s nepriateľom.
     *
     * @param projectile Projektil na skontrolenie
     * @param entity Entita na skontrolenie
     * @return {@code true}, ak došlo k zrážke, inak {@code false}
     */
    private boolean checkProjectileCollision(Projectile projectile, Entity entity) {
        if (new Rectangle((int)projectile.getX() - 5, (int)projectile.getY() - 5, 10, 10).intersects(new Rectangle(entity.getX(), entity.getY(), 50, 50))) {
            int damage = projectile.getDamage();
            entity.takeDamage(damage);
            return true;
        }
        return false;
    }

    /**
     * Získa zoznam všetkých aktuálnych projektilov.
     *
     * @return Zoznam projektilov
     */
    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }

}

