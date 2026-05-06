package object;

import entity.Enemy;
import projectile.CanonBall;

import java.util.ArrayList;

/**
 * Trieda predstavujúca delo (kanón), ktoré môže zamerať a vystreliť na zombie.
 */
public class Canon {

    private final int x;
    private final int y;
    private int hp;
    private double angle;
    private static final int RANGE = 400;
    private int cooldown = 0;
    private static final int COOLDOWN_MAX = 30;
    private final int damage;
    private final ArrayList<CanonBall> projectiles = new ArrayList<>();

    private int lifetime;

    /**
     * Vytvorí kanón na pozícii (x, y) s daným množstvom životov.
     *
     * @param x  X súradnica kanóna (v mriežke).
     * @param y  Y súradnica kanóna (v mriežke).
     * @param hp Počiatočné životy kanóna.
     */
    public Canon(int x, int y, int hp) {
        this.angle = 0;
        this.damage = 25;
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    /**
     * Nastaví uhol natočenia kanóna v stupňoch.
     *
     * @param angle Uhol natočenia v stupňoch.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Získá X súradnicu kanóna.
     *
     * @return X súradnica.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Získá Y súradnicu kanóna.
     *
     * @return Y súradnica.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Skontroluje, či je kanón zničený (životy sú <= 0).
     *
     * @return {@code true}, ak je kanón zničený, inak {@code false}.
     */
    public boolean isDestroyed() {
        return this.hp <= 0;
    }

    /**
     * Získá aktuálny uhol natočenia kanóna v stupňoch.
     *
     * @return Uhol natočenia.
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Aktualizuje stav kanóna, hľadá najbližšieho zombie,
     * natočí sa na neho, a ak je v dosahu, vystrelí.
     * Kanón má aj cooldown medzi výstrelmi a limitovaný čas života.
     *
     * @param zombies Zoznam všetkých zombie v hre.
     */
    public void update(ArrayList<Enemy> enemies) {
        if (this.cooldown > 0) {
            this.cooldown--;
            return;
        }

        Enemy closest = this.findClosestEnemy(enemies);
        if (closest != null) {
            double dx = closest.getX() - (this.x * 20 + 20);
            double dy = closest.getY() - (this.y * 20 + 20);
            this.angle = Math.toDegrees(Math.atan2(dy, dx));

            if (Math.sqrt(dx * dx + dy * dy) < RANGE) {
                this.lifetime++;
                if (this.lifetime > 20) {
                    this.hp = 0;
                }
                this.shoot();
                this.cooldown = COOLDOWN_MAX;
            }
        }
    }

    /**
     * Nájde najbližšieho zombie ku kanónu.
     *
     * @param zombies Zoznam všetkých zombie.
     * @return Najbližší zombie, alebo {@code null}, ak zoznam je prázdny.
     */
    private Enemy findClosestEnemy(ArrayList<Enemy> enemies) {
        Enemy closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Enemy enemy : enemies) {
            double distance = Math.sqrt(
                    Math.pow(enemy.getX() - (this.x * 20 + 20), 2) +
                            Math.pow(enemy.getY() - (this.y * 20 + 20), 2));

            if (distance < minDistance) {
                minDistance = distance;
                closest = enemy;
            }
        }
        return closest;
    }

    /**
     * Vystrelí kanón na aktuálny uhol.
     * Vytvorí nový projektíl (CanonBall) a pridá ho do zoznamu projektílov.
     */
    private void shoot() {
        double angleRad = Math.toRadians(this.angle);
        int startX = this.x * 20 + 20;
        int startY = this.y * 20 + 20;
        int projectileSpeed = 8;
        this.projectiles.add(new CanonBall(
                startX,
                startY,
                projectileSpeed * Math.cos(angleRad),
                projectileSpeed * Math.sin(angleRad),
                this.damage
        ));
    }

    /**
     * Získá zoznam všetkých aktuálnych projektílov vystrelených kanónom.
     *
     * @return Zoznam projektílov.
     */
    public ArrayList<CanonBall> getProjectiles() {
        return this.projectiles;
    }

}
