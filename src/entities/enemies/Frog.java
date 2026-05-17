package entities.enemies;

import graphics.entityGraphics.EnemyGraphics;
import graphics.entityGraphics.FrogGraphics;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Trieda Frog predstavuje žabu ako typ nepriateľa, ktorý náhodne skáče smerom k cieľu.
 * Dedi od {@link Enemy}
 */
public class Frog extends Enemy {

    private boolean isJumping = false;
    private final Random random = new Random();

    /**
     * Vytvorí novú žabu s danými parametrami.
     *
     * @param health počet životov
     * @param damage sila útoku
     * @param x      počiatočná X-ová pozícia
     * @param y      počiatočná Y-ová pozícia
     * @param speed  rýchlosť pohybu
     */
    public Frog(int health, int damage, int x, int y, int speed) {
        super(health, damage, x, y, speed, 50);
        this.setupJumpTimer();
    }

    /**
     * Syntax z časovasu bol inšpirovany z https://www.baeldung.com/java-timer-and-timertask
     *
     * Nastaví časovač, ktorý pravidelne obnovuje možnosť žaby skákať.
     * Skákanie je možné po uplynutí náhodného intervalu medzi 1 a 3 sekundami.
     */
    private void setupJumpTimer() {
        Timer jumpTimer = new Timer();
        jumpTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Frog.this.isJumping = false;
            }
        }, 0, 1000 + this.random.nextInt(2000));
    }

    /**
     * Realizuje skok smerom k cieľovej pozícii, ak žaba nie je momentálne v skoku.
     * Skok sa vykonáva s určitým náhodným rozptylom.
     *
     * @param targetX cieľová X-ová pozícia
     * @param targetY cieľová Y-ová pozícia
     */
    @Override
    public void attack(int targetX, int targetY) {
        if (this.isJumping) {
            return;
        }

        this.isJumping = true;

        int lengthX =  targetX - super.getX();
        int lengthY = targetY - super.getY();


        super.addToX(lengthX + this.random.nextInt(50) - 25);
        super.addToY(lengthY + this.random.nextInt(50) - 25);

        double angle = Math.toDegrees(Math.atan2(lengthY, lengthX));
        super.setAngle(angle);

    }

    @Override
    public EnemyGraphics getGraphics() {
        return new FrogGraphics(this);
    }
}
