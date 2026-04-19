package entity;

import weapon.Hand;
import weapon.Weapon;

/**
 * Trieda {@code Player} reprezentuje hráča v hre. Uchováva informácie o jeho pozícii, zdrojoch, zdraví, zbrani,
 * skóre a ďalších vlastnostiach.
 */
public class Player {

    private int x;
    private int y;
    private int wood;
    private int stone;
    private int gold;
    private int health;
    private double angle;
    private final int speed;
    private Weapon weapon;
    private final int maxHealth = 100;
    private int score;

    /**
     * Vytvorí nového hráča so zadanou pozíciou a rýchlosťou.
     *
     * @param x     X-ová pozícia hráča
     * @param y     Y-ová pozícia hráča
     * @param speed rýchlosť pohybu hráča
     */
    public Player(int x, int y, int speed) {
        this.score = 0;
        this.x = x;
        this.y = y;
        this.angle = 0;
        this.speed = speed;
        this.weapon = new Hand(30, 10);
        this.health = this.maxHealth;
    }

    /**
     * @return {@code true}, ak má hráč 0 alebo menej zdravia.
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Nastaví uhol, ktorým sa hráč pozerá (v stupňoch).
     *
     * @param angle uhol v stupňoch
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /** @param x nová X-ová pozícia hráča */
    public void setX(int x) {
        this.x = x;
    }

    /** @param y nová Y-ová pozícia hráča */
    public void setY(int y) {
        this.y = y;
    }

    /** @return X-ová pozícia hráča */
    public int getX() {
        return this.x;
    }

    /** @return Y-ová pozícia hráča */
    public int getY() {
        return this.y;
    }

    /** @return rýchlosť hráča */
    public int getSpeed() {
        return this.speed;
    }

    /** @return aktuálny uhol hráča v stupňoch */
    public double getAngle() {
        return this.angle;
    }

    /** @return aktuálne zvolená zbraň hráča */
    public Weapon getZbran() {
        return this.weapon;
    }

    /**
     * Nastaví množstvo zdravia hráča.
     *
     * @param health nové zdravie
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Nastaví hráčovi novú zbraň.
     *
     * @param weapon nová zbraň
     */
    public void setZbran(Weapon weapon) {
        this.weapon = weapon;
    }

    /** @return množstvo dreva v inventári hráča */
    public int getWood() {
        return this.wood;
    }

    /** @return množstvo kameňa v inventári hráča */
    public int getStone() {
        return this.stone;
    }

    /** @return množstvo zlata v inventári hráča */
    public int getGold() {
        return this.gold;
    }

    /**
     * Pridá hráčovi drevo a zvýši skóre.
     *
     * @param amount počet jednotiek dreva
     */
    public void addWood(int amount) {
        this.score += amount * 10;
        this.wood += amount;
    }

    /**
     * Pridá hráčovi kameň a zvýši skóre.
     *
     * @param amount počet jednotiek kameňa
     */
    public void addStone(int amount) {
        this.score += amount * 10;
        this.stone += amount;
    }

    /**
     * Pridá hráčovi zlato a zvýši skóre.
     *
     * @param amount počet jednotiek zlata
     */
    public void addGold(int amount) {
        this.score += amount * 10;
        this.gold += amount;
    }

    /**
     * Odpočíta hráčovi zdravie podľa prijatého poškodenia.
     *
     * @param damage množstvo poškodenia
     */
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    /** @return aktuálne zdravie hráča */
    public int getHealth() {
        return this.health;
    }

    /** @return maximálne zdravie hráča */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /** @return aktuálne skóre hráča */
    public int getScore() {
        return this.score;
    }

    /**
     * Resetuje hráčove zdroje, skóre a nastaví základnú zbraň.
     */
    public void reset() {
        this.wood = 0;
        this.stone = 0;
        this.gold = 0;
        this.score = 0;
        this.setZbran(new Hand(30, 10));
    }
}
