package projectile;

/**
 * Trieda reprezentuje projektil typu kanónová guľa,
 * ktorá sa pohybuje po hernej ploche s danou rýchlosťou a spôsobuje poškodenie.
 */
public class CanonBall {
    private double x;
    private double y;
    private final double dx;
    private final double dy;
    private final int damage;
    private int lifetime = 120;

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
        this.x = startX;
        this.y = startY;
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
    }

    /**
     * Aktualizuje pozíciu kanónovej gule podľa jej rýchlosti a znižuje zostávajúci čas života.
     */
    public void update() {
        this.x += this.dx;
        this.y += this.dy;
        this.lifetime--;
    }

    /**
     * Určuje, či by mala byť kanónová guľa odstránená (ak jej životnosť vypršala
     * alebo ak sa dostala mimo platnú oblasť).
     *
     * @return {@code true}, ak by mala byť guľa odstránená, inak {@code false}
     */
    public boolean shouldRemove() {
        return this.lifetime <= 0 || this.x < 0 || this.y < 0;
    }

    /**
     * Vráti aktuálnu X súradnicu kanónovej gule.
     *
     * @return aktuálna X súradnica
     */
    public double getX() {
        return this.x;
    }

    /**
     * Vráti aktuálnu Y súradnicu kanónovej gule.
     *
     * @return aktuálna Y súradnica
     */
    public double getY() {
        return this.y;
    }

    /**
     * Vráti hodnotu poškodenia, ktoré spôsobuje kanónová guľa.
     *
     * @return spôsobené poškodenie
     */
    public int getDamage() {
        return this.damage;
    }
}
