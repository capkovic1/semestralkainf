package graphics.entityGraphics;

import java.awt.Graphics;

/**
 * Interface reprezentujúci grafiku nepriateľa.
 * Poskytuje metódy na vykreslenie nepriateľa a efektu jeho smrti.
 */
public interface EnemyGraphics {
    /**
     * Vykreslí nepriateľa na obrazovku.
     *
     * @param g grafický kontext na kreslenie
     */
    void draw(Graphics g);

    /**
     * Vykreslí efekt smrti nepriateľa (napr. výbuch, zmiznutie).
     *
     * @param g grafický kontext na kreslenie
     */
    void drawDeathEffect(Graphics g);
}
