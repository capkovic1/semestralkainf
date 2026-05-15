package managers;

import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;
import projectile.Projectile;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manažér projektilov zodpovedný za správu a aktualizáciu všetkých projektilov v hre.
 *
 * Udržiava interný zoznam projektilov, stará sa o ich pohyb, kolízie s nepriateľmi
 * a ich odstránenie, keď už projektily nie sú potrebné.
 */
public class ProjectileManager {
    private static ProjectileManager instance;
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    /**
     * Pridá nový projektil do správy.
     *
     * @param projectile projektil, ktorý sa má pridať
     */
    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    /**
     * Singleton getter pre inštanciu ProjectileManager.
     *
     * @return jediná inštancia ProjectileManager
     */
    public static ProjectileManager getInstance() {
        if (instance == null) {
            instance = new ProjectileManager();
        }
        return instance;
    }

    /**
     * Aktualizuje všetky projektily: pohyb, kontrola kolízií s nepriateľmi/hráčom
     * a odstránenie projektilov, ktoré zasiahli cieľ alebo sú neplatné.
     *
     * @param enemies zoznam nepriateľov v hre
     * @param enemyManager manažér nepriateľov (pre pridanie damage indikátorov)
     * @param player hráč (na kontrolu kolícií projektilov s hráčom)
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
     * Skontroluje kolíciu medzi projektilom a entitou. Pri zásahu aplikuje poškodenie.
     *
     * @param projectile projektil na skontrolovanie
     * @param entity entita, s ktorou sa má kontrolovať kolízia
     * @return {@code true}, ak došlo k zásahu, inak {@code false}
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
     * Vráti zoznam všetkých aktuálnych projektilov.
     *
     * @return modifikovateľný zoznam projektilov
     */
    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }

}
