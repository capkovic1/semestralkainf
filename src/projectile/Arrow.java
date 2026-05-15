package projectile;

import graphics.projectilesGraphics.ArrowGraphics;

import java.awt.Graphics;

/**
 * Projektil typu šíp (Arrow) používaný pri streľbe z luku.
 * Dedí základné správanie z {@link Projectile} a nastavuje počiatočné smerovanie a životnosť.
 */
public class Arrow extends Projectile {

    /**
     * Vytvorí nový šíp so zadanou počiatočnou pozíciou, rýchlosťou, poškodením a smerom (dx, dy).
     *
     * @param x      počiatočná X súradnica projektilu
     * @param y      počiatočná Y súradnica projektilu
     * @param speed  rýchlosť pohybu projektilu
     * @param damage množstvo poškodenia, ktoré projektil spôsobuje
     * @param dx     rýchlostný posun na osi X
     * @param dy     rýchlostný posun na osi Y
     */
    public Arrow(double x, double y, int speed, int damage, double dx, double dy) {
        super(x, y, speed, damage);
        super.setDx(dx);
        super.setDy(dy);
        this.setLifetime(60);
    }

    /**
     * Aktualizuje pozíciu šípu pridaním rýchlostných komponentov a znižuje jeho životnosť.
     */
    @Override
    public void update() {
        this.setX(this.getX() + super.getDx());
        this.setY(this.getY() + super.getDy());
        this.decreaseLifetime();
    }

    /**
     * Určuje, či by mal byť šíp odstránený (ak vypršala jeho životnosť alebo odišiel mimo obrazovku).
     *
     * @return {@code true}, ak má byť šíp odstránený, inak {@code false}
     */
    @Override
    public boolean shouldRemove() {
        return this.getLifetime() <= 0 || this.getX() < 0 || this.getY() < 0;
    }

    /**
     * Vykreslí šíp pomocou {@link ArrowGraphics} na zadanom grafickom kontexte.
     *
     * @param g grafický kontext
     */
    @Override
    public void draw(Graphics g) {
        new ArrowGraphics(this).draw(g);
    }
}
