package projectile;

import java.awt.Graphics;

/**
 * Abstraktná trieda reprezentujúca projektil s vlastnosťami ako pozícia, rýchlosť,
 * poškodenie, uhol a stav aktivity.
 * <p>
 * Slúži ako základ pre špecifické typy projektilov, ktoré môžu dedičstvom rozšíriť
 * základné správanie a pridávať ďalšie funkcionality.
 * </p>
 */
public abstract class Projectile {
    private double x;
    private double y;
    private double dx;
    private double dy;
    
    private int lifetime;
    
    private final int speed;
    private boolean active = true;
    private double angle;
    private int damage;

    /**
     * Vytvorí nový projektil so zadanou počiatočnou pozíciou, rýchlosťou a poškodením.
     *
     * @param x       počiatočná X súradnica projektilu
     * @param y       počiatočná Y súradnica projektilu
     * @param speed   rýchlosť pohybu projektilu
     * @param damage  množstvo poškodenia, ktoré projektil spôsobuje
     */
    public Projectile(double x, double y, int speed, int damage) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
    }

    public int getLifetime() {
        return this.lifetime;
    }
    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }
    public void decreaseLifetime() {
        this.lifetime--;
    }
    /**
     * Získa aktuálnu X súradnicu projektilu.
     *
     * @return aktuálna X súradnica
     */
    public double getX() {
        return this.x;
    }

    /**
     * Získa aktuálnu Y súradnicu projektilu.
     *
     * @return aktuálna Y súradnica
     */
    public double getY() {
        return this.y;
    }

    /**
     * Získa rýchlosť projektilu.
     *
     * @return rýchlosť projektilu
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Určuje, či je projektil aktívny (či je stále v hre).
     *
     * @return {@code true}, ak je projektil aktívny, inak {@code false}
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Získa množstvo poškodenia, ktoré projektil spôsobuje.
     *
     * @return poškodenie projektilu
     */
    public int getDamage() {
        return this.damage;
    }

    protected void setDx(double dx) {
        this.dx = dx;
    }
    protected void setDy(double dy) {
        this.dy = dy;
    }
    public double getDx() {
        return this.dx;
    }
    public double getDy() {
        return this.dy;
    }
    /**
     * Získa uhol, pod ktorým sa projektil pohybuje.
     *
     * @return uhol pohybu v stupňoch
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Nastaví novú X súradnicu projektilu.
     *
     * @param x nová X súradnica
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Nastaví novú Y súradnicu projektilu.
     *
     * @param y nová Y súradnica
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Nastaví, či je projektil aktívny.
     *
     * @param active {@code true} ak má byť projektil aktívny, inak {@code false}
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Nastaví uhol pohybu projektilu.
     *
     * @param angle nový uhol pohybu v stupňoch
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Nastaví množstvo poškodenia projektilu.
     *
     * @param damage nové poškodenie
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Aktualizuje stav projektilu (pohyb, znižovanie lifetime, atď.).
     * Každá podtrieda musí implementovať svoju vlastnú logiku.
     */
    public abstract void update();

    /**
     * Určuje, či by mal byť projektil odstránený z hry.
     * Každá podtrieda musí implementovať svoju vlastnú logiku.
     *
     * @return {@code true}, ak by mal byť projektil odstránený, inak {@code false}
     */
    public abstract boolean shouldRemove();
    /**
     * Vykreslí projektil na zadaném grafickém kontextu.
     * Každá podtrieda musí implementovat svou vlastní logiku pro vykreslení.
     *
     * @param g grafický kontext pro vykreslení
     */
    public abstract void draw(Graphics g);
}
