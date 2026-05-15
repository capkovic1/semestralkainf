package graphics.objectGraphics.materialGraphics;


import java.awt.Graphics;

/**
 * Interface pre grafiku materiálov (dreva, kameňa, atď.).
 * Poskytuje metódu na vykreslenie materiálu na obrazovku.
 */
public interface MaterialGraphics {
    /**
     * Vykreslí materiál na obrazovku.
     *
     * @param g grafický kontext na kreslenie
     */
    void draw(Graphics g);
}
