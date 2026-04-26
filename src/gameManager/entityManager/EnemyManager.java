package gameManager.entityManager;

import entity.Player;
import java.awt.Graphics;

/**
 * Rozhranie pre správu nepriateľov v hre.
 * Definuje metódy na vytváranie, aktualizáciu, kreslenie a vymazanie nepriateľov.
 */
public interface EnemyManager {
    /**
     * Vytvorí zadaný počet nepriateľov usporiadaných do mriežky podľa počtu stĺpcov a riadkov.
     *
     * @param count počet nepriateľov na vytvorenie
     * @param cols počet stĺpcov v rozložení nepriateľov
     * @param rows počet riadkov v rozložení nepriateľov
     */
    void createEnemies(int count, int cols, int rows);

    /**
     * Aktualizuje stav nepriateľov podľa pozície hráča a vykreslí ich na danom grafe.
     *
     * @param player inštancia hráča, ktorú nepriatelia môžu sledovať alebo na ňu reagovať
     * @param g grafický kontext na vykresľovanie nepriateľov
     */
    void updateEnemies(Player player , Graphics g);
    /**
     * Vykreslí všetkých nepriateľov na poskytnutom grafickom kontexte.
     *
     * @param g grafický kontext, na ktorý sa nepriatelia vykreslia
     */
    void drawEnemies(Graphics g);
    /**
     * Odstráni všetkých nepriateľov zo zoznamu.
     */
    void clearEnemies();
}
