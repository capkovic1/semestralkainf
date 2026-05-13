package entities.player;

import entities.Entity;
import resource.ResourceType;
import weapon.Hand;
import weapon.Weapon;

/**
 * Trieda {@code Player} reprezentuje hráča v hre. Uchováva informácie o jeho pozícii, zdrojoch, zdraví, zbrani,
 * skóre a ďalších vlastnostiach.
 */
public class Player implements Entity {

    private int x;
    private int y;
    private int wood;
    private int stone;
    private int gold;
    private int health;
    private double angle;
    private int speed;
    private int efficiency;
    private int damage;
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
        this.efficiency = 3;
        this.damage = 3;
        this.weapon = new Hand(30, 10);
        this.health = this.maxHealth;
    }

    public void setDefaultStrength() {
        this.damage = 3;
    }
    public void setDefaultSpeed() {
        this.speed = 5;
    }
    public void setDefaultEfficiency() {
        this.efficiency = 3;
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

    public int getDamage() {
        return this.damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    /** @return aktuálny uhol hráča v stupňoch */
    public double getAngle() {
        return this.angle;
    }
    public int getEfficiency() {
        return this.efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }
    /** @return aktuálne zvolená zbraň hráča */
    public Weapon getWeapon() {
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
    public void setWeapon(Weapon weapon) {
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

    public void addResource(ResourceType resourceType , int amount) {
        this.score += amount * 10;

        switch (resourceType) {
            case WOOD -> this.wood+=amount;
            case STONE -> this.stone+=amount;
            case GOLD -> this.gold+=amount;
        }
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    /**
     * Resetuje hráčove zdroje, skóre a nastaví základnú zbraň.
     */
    public void reset() {
        this.wood = 0;
        this.stone = 0;
        this.gold = 0;
        this.score = 0;
        this.setWeapon(new Hand(30, 10));
    }
}
