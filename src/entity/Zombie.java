package entity;

import graphics.entityGraphics.EnemyGraphics;
import graphics.entityGraphics.ZombieGraphics;

/**
 * Trieda {@code Zombie} reprezentuje nepriateľa, ktorý sa priamo pohybuje k cieľu.
 * Dedi z abstraktnej triedy {@link Enemy}
 * Nevykonáva diaľkový útok – namiesto toho sa približuje k hráčovi, aby ho mohol zasiahnuť.
 */
public class Zombie extends Enemy {
    /**
     * Vytvorí nového zombíka so zadanými vlastnosťami.
     *
     * @param health počiatočné zdravie
     * @param damage sila útoku
     * @param x počiatočná pozícia X
     * @param y počiatočná pozícia Y
     * @param speed rýchlosť pohybu
     */
    public Zombie(int health, int damage, int x, int y, int speed) {
        super(health, damage, x, y, speed, 50);
    }
    /**
     * Pohne zombíka smerom k cieľu na základe jeho rýchlosti.
     * Tiež nastaví uhol pohľadu podľa smeru pohybu.
     *
     * @param targetX súradnica X cieľa
     * @param targetY súradnica Y cieľa
     */
    public void attack(int targetX, int targetY) {
        int x = targetX - super.getX();
        int y = targetY - super.getY();

        double length = Math.sqrt(x * x + y * y);
        if (length > 0) {
            x = (int)(x / length * super.getSpeed());
            y = (int)(y / length * super.getSpeed());
        }

        super.addToX(x);
        super.addToY(y);

        double angle = Math.toDegrees(Math.atan2(y, x));
        super.setAngle(angle);
    }

    @Override
    public EnemyGraphics getGraphics() {
        return new ZombieGraphics(this);
    }

}
