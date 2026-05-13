package entities.enemies;

import entities.Entity;
import graphics.entityGraphics.EnemyGraphics;

/**
 * Abstraktná trieda Enemy reprezentuje nepriateľa v hre.
 * Obsahuje základné vlastnosti ako zdravie, pozíciu, uhol natočenia, rýchlosť a poškodenie.
 * Trieda má metódy na manipuláciu s pozíciou a stavom nepriateľa.
 */
public abstract class Enemy implements Entity {
    private int health;
    private final int speed;
    private final int damage;
    private int x;
    private int y;
    private double angle;
    private final int goldReward;

    /**
     * Konštruktor pre vytvorenie nepriateľa.
     *
     * @param health počet životov (zdravie)
     * @param damage sila útoku (poškodenie)
     * @param x      počiatočná X-ová pozícia
     * @param y      počiatočná Y-ová pozícia
     * @param speed  rýchlosť pohybu
     */
    public Enemy(int health, int damage , int x , int y , int speed , int goldReward) {
         this.goldReward = goldReward;
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;

        this.angle = 0;
        this.speed = speed;
    }
    /**
     * Získa aktuálny uhol natočenia nepriateľa.
     *
     * @return uhol v stupňoch alebo radiánoch (podľa implementácie)
     */
    public double getAngle() {
        return this.angle;
    }
    /**
     * Zistí, či je nepriateľ mŕtvy (životy ≤ 0).
     *
     * @return true ak je mŕtvy, inak false
     */
    public boolean isDead() {
        return this.getHealth() <= 0;
    }
    /**
     * Nastaví nový uhol natočenia nepriateľa.
     *
     * @param angle nový uhol
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }
    /**
     * Posunie nepriateľa v osi X o danú hodnotu.
     *
     * @param x posun v X (kladný alebo záporný)
     */
    public void addToX(int x) {
        this.x += x;
    }
    /**
     * Posunie nepriateľa v osi Y o danú hodnotu.
     *
     * @param y posun v Y (kladný alebo záporný)
     */
    public void addToY(int y) {
        this.y += y;
    }
    /**
     * Získa rýchlosť nepriateľa.
     *
     * @return rýchlosť pohybu
     */
    protected int getSpeed() {
        return this.speed;
    }
    /**
     * Odpočíta nepriateľovi životy.
     *
     * @param damage počet životov, o ktoré má prísť (poškodenie)
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }
    /**
     * Nastaví pozíciu X nepriateľa.
     *
     * @param x nová pozícia X
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Nastaví pozíciu Y nepriateľa.
     *
     * @param y nová pozícia Y
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Získa aktuálne životy nepriateľa.
     *
     * @return aktuálne zdravie
     */
    public int getHealth() {
        return this.health;
    }
    /**
     * Získa silu útoku nepriateľa.
     *
     * @return poškodenie spôsobené útokom
     */
    public int getDamage() {
        return this.damage;
    }
    /**
     * Získa aktuálnu X-ovú pozíciu nepriateľa.
     *
     * @return X pozícia
     */
    public int getX() {
        return this.x;
    }
    /**
     * Získa aktuálnu Y-ovú pozíciu nepriateľa.
     *
     * @return Y pozícia
     */
    public int getY() {
        return this.y;
    }
    public int getGoldReward() {
        return this.goldReward;
    };
    /**
     * Abstraktná metóda pre útok nepriateľa na pozíciu x a y.
     *
     * @param targetX X-ová pozícia
     * @param targetY Y-ová pozícia
     */
    public abstract void attack(int targetX, int targetY);

    public abstract EnemyGraphics getGraphics();

}
