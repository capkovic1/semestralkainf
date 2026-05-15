package projectile;

import graphics.projectilesGraphics.CanonBallGraphics;

import java.awt.Graphics;

/**
 * Trieda reprezentuje projektil typu kanónová guľa,
 * ktorá sa pohybuje po hernej ploche s danou rýchlosťou a spôsobuje poškodenie.
 */
public class CanonBall extends Projectile {
    private final double dx;
    private final double dy;

    /**
     * Vytvorí novú kanónovú guľu na pozícii (startX, startY) s daným posunom (dx, dy) a poškodením.
     *
     * @param startX počiatočná X súradnica projektilu
     * @param startY počiatočná Y súradnica projektilu
     * @param dx posun v smere osi X (rýchlosť pohybu na osi X)
     * @param dy posun v smere osi Y (rýchlosť pohybu na osi Y)
     * @param damage spôsobené poškodenie pri zásahu
     */
    public CanonBall(double startX, double startY, double dx, double dy, int damage) {
        super(startX, startY, 8, damage);
        this.dx = dx;
        this.dy = dy;
        this.setLifetime(120);
    }

    /**
     * Aktualizuje pozíciu kanónovej gule podľa jej rýchlosti a znižuje zostávajúci čas života.
     */
    @Override
    public void update() {
        this.setX(this.getX() + this.dx);
        this.setY(this.getY() + this.dy);
        this.decreaseLifetime();
    }

    /**
     * Určuje, či by mala byť kanónová guľa odstránená (ak jej životnosť vypršala
     * alebo ak sa dostala mimo platnú oblasť).
     *
     * @return {@code true}, ak by mala byť guľa odstránená, inak {@code false}
     */
    @Override
    public boolean shouldRemove() {
        return this.getLifetime() <= 0 || this.getX() < 0 || this.getY() < 0;
    }
    public void draw(Graphics g) {
        new CanonBallGraphics(this).draw(g);
    }
}

