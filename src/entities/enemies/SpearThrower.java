package entities.enemies;

import graphics.entityGraphics.EnemyGraphics;
import graphics.entityGraphics.SpearThrowerGraphics;
import managers.ProjectileManager;
import projectile.Spear;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;
/**
 * Trieda SpearThrower reprezentuje nepriateľa, ktorý vrhá oštepy smerom k cieľu.
 * Dedí z triedy {@link Enemy}
 * Oštepy vrhá s časovým obmedzením a spravuje ich vnútorne pomocou zoznamu.
 */
public class SpearThrower extends Enemy {
    private final Timer attackTimer;
    private boolean canAttack = true;
    /**
     * Vytvorí nového SpearThrowera s danými vlastnosťami.
     *
     * @param health počiatočné zdravie
     * @param damage sila jedného oštepu
     * @param x počiatočná pozícia na osi X
     * @param y počiatočná pozícia na osi Y
     * @param speed rýchlosť pohybu
     */
    public SpearThrower(int health, int damage, int x, int y, int speed) {
        super(health, damage, x, y, speed , 100);
        this.attackTimer = new Timer();
    }
    /**
     * Zaútočí na cieľ, ak nie je v cooldown režime.
     * Vytvorí nový oštep a hodí ho na cieľové súradnice.
     * Po útoku nastane 2-sekundový cooldown.
     *
     * @param targetX súradnica X cieľa
     * @param targetY súradnica Y cieľa
     */
    @Override
    public void attack(int targetX, int targetY) {
        if (this.canAttack) {
            Spear spear = new Spear(this.getX(), this.getY(), 10, this.getDamage());
            spear.throwSpear(targetX, targetY);
            ProjectileManager.getInstance().addProjectile(spear);

            this.canAttack = false;
            this.attackTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SpearThrower.this.canAttack = true;
                }
            }, 2000);
        }
    }

    @Override
    public EnemyGraphics getGraphics() {
        return new SpearThrowerGraphics(this);
    }


}

