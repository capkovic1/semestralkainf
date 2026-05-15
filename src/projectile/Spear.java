package projectile;

import graphics.projectilesGraphics.SpearGraphics;

import java.awt.Graphics;

/**
 * Trieda {@code Spear} reprezentuje vrhací oštep, ktorý je podtriedou {@link Projectile}.
 * <p>
 * Oštep sa pohybuje smerom k cieľovej pozícii a po dosiahnutí cieľa sa deaktivuje.
 * </p>
 */
public class Spear extends Projectile {
    /**
     * Vytvorí novú inštanciu {@code Spear} s danou počiatočnou pozíciou, rýchlosťou a poškodením.
     *
     * @param x      počiatočná X súradnica
     * @param y      počiatočná Y súradnica
     * @param speed  rýchlosť pohybu oštepu
     * @param damage poškodenie, ktoré oštep spôsobí
     */
    public Spear(int x, int y, int speed, int damage) {
        super(x, y, speed, damage);
    }

    /**
     * Inicializuje pohyb oštepu smerom k cieľovej pozícii.
     *
     * @param targetX cieľová X súradnica
     * @param targetY cieľová Y súradnica
     */
    public void throwSpear(double targetX, double targetY) {
        super.setDx(targetX);
        super.setDy(targetY);
        super.setActive(true);

        double dx = targetX - this.getX();
        double dy = targetY - this.getY();
        double angleRad = Math.atan2(dy, dx);
        this.setAngle(angleRad);
    }

    /**
     * Aktualizuje pozíciu oštepu na základe jeho rýchlosti a uhla.
     * Keď oštep dosiahne cieľovú pozíciu alebo je dostatočne blízko, deaktivuje sa.
     */
    @Override
    public void update() {
        if (!super.isActive()) {
            return;
        }

        int newX = (int)(this.getX() + Math.cos(this.getAngle()) * this.getSpeed());
        int newY = (int)(this.getY() + Math.sin(this.getAngle()) * this.getSpeed());

        this.setX(newX);
        this.setY(newY);

        if (Math.hypot(super.getDx() - newX, super.getDy() - newY) <= this.getSpeed()) {
            this.setX(super.getDx());
            this.setY(super.getDy());
            super.setActive(false);
        }
    }

    /**
     * Určuje, či by mal byť oštep odstránený.
     *
     * @return {@code true}, ak je oštep neaktívny, inak {@code false}
     */
    @Override
    public boolean shouldRemove() {
        return !super.isActive();
    }

    /**
     * Zisťuje, či je oštep stále aktívny.
     *
     * @return {@code true}, ak je oštep aktívny, inak {@code false}
     */
    public boolean isActive() {
        return super.isActive();
    }

    public void draw(Graphics g) {
        new SpearGraphics(this).draw(g);
    }
}

