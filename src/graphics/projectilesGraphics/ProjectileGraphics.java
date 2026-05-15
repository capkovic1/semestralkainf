package graphics.projectilesGraphics;

import java.awt.Graphics;

/**
 * Interface pre grafiku projektilov.
 * Poskytuje metódu na vykreslenie projektilu na obrazovku.
 */
public interface ProjectileGraphics {
    /**
     * Vykreslí projektil na obrazovku.
     *
     * @param g grafický kontext na kreslenie
     */
    void draw(Graphics g);
}
