package entities;

/**
 * Interface reprezentujúci všetky entity v hre (hráči, nepriatelia).
 * Poskytuje základné metódy pre prácu s pozíciou a poškodením.
 */
public interface Entity {
    /**
     * Získa aktuálnu X-ovu súradnicu entity.
     *
     * @return X-ová pozícia
     */
    int getX();

    /**
     * Získa aktuálnu Y-ovu súradnicu entity.
     *
     * @return Y-ová pozícia
     */
    int getY();

    /**
     * Aplikuje poškodenie na entitu (znižuje zdravie).
     *
     * @param damage množstvo poškodenia
     */
    void takeDamage(int damage);
}
