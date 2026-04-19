package entity;

import graphics.entityGraphics.EnemyGraphics;
import graphics.entityGraphics.WolfGraphics;

/**
 * Trieda reprezentuje nepriateľa typu {@code Wolf}, ktorý sa pohybuje smerom k cieľu
 * a periodicky (každých 5 sekúnd) sa na 2 sekundy zrýchli.
 */
public class Wolf extends Enemy {

    private final long time;
    /**
     * Vytvára novú inštanciu vlka.
     *
     * @param health počiatočné zdravie
     * @param damage sila útoku
     * @param x počiatočná pozícia X
     * @param y počiatočná pozícia Y
     * @param speed rýchlosť pohybu
     */
    public Wolf(int health, int damage, int x, int y, int speed) {
        super(health, damage, x, y, speed);
        this.time = System.currentTimeMillis();
    }
    /**
     * Útočí pohybom smerom k cieľu. Vlk sa každých 5 sekúnd na 2 sekundy zrýchli.
     *
     * @param targetX súradnica X cieľa
     * @param targetY súradnica Y cieľa
     */
    public void attack(int targetX, int targetY) {
        int x = targetX - super.getX();
        int y = targetY - super.getY();
        double length = Math.sqrt(x * x + y * y);

        long helpTime = System.currentTimeMillis();
        long difference = helpTime - this.time;

        int effectiveSpeed = super.getSpeed();

        if (difference % 5000 < 2000) {
            effectiveSpeed *= 2;
        }
        if (length > 0) {
            x = (int)(x / length * effectiveSpeed);
            y = (int)(y / length * effectiveSpeed);
        }

        super.addToX(x);
        super.addToY(y);

        double angle = Math.toDegrees(Math.atan2(y, x));
        super.setAngle(angle);
    }

    @Override
    public int getGoldReward() {
        return 75;
    }

    @Override
    public EnemyGraphics getGraphics() {
        return new WolfGraphics(this);
    }
}
