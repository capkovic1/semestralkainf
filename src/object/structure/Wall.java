package object.structure;

/**
 * Trieda reprezentuje stenu v hre, umiestnenú na súradniciach (x, y).
 * Aktuálne neobsahuje informácie o životoch alebo poškodení.
 */
public class Wall implements Structure {
    private final int x;
    private final int y;

    /**
     * Vytvorí novú stenu na daných súradniciach.
     * Parameter {@code hp} je momentálne nevyužitý.
     *
     * @param x súradnica X steny
     * @param y súradnica Y steny
     */
    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vráti súradnicu X steny.
     *
     * @return súradnica X
     */
    public int getX() {
        return this.x;
    }

    /**
     * Vráti súradnicu Y steny.
     *
     * @return súradnica Y
     */
    public int getY() {
        return this.y;
    }
}
