package projectile;

import graphics.projectilesGraphics.ArrowGraphics;

import java.awt.Graphics;

public class Arrow extends Projectile {

    /**
     * Vytvorí nový projektil so zadanou počiatočnou pozíciou, rýchlosťou a poškodením.
     *
     * @param x      počiatočná X súradnica projektilu
     * @param y      počiatočná Y súradnica projektilu
     * @param speed  rýchlosť pohybu projektilu
     * @param damage množstvo poškodenia, ktoré projektil spôsobuje
     */
    public Arrow(double x, double y, int speed, int damage, double dx, double dy) {
        super(x, y, speed, damage);
        super.setDx(dx);
        super.setDy(dy);
        this.setLifetime(60);
    }

    @Override
    public void update() {
        this.setX(this.getX() + super.getDx());
        this.setY(this.getY() + super.getDy());
        this.decreaseLifetime();
    }

    @Override
    public boolean shouldRemove() {
        return this.getLifetime() <= 0 || this.getX() < 0 || this.getY() < 0;
    }

    @Override
    public void draw(Graphics g) {
        new ArrowGraphics(this).draw(g);
    }
}
