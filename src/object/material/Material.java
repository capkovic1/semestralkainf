package object.material;

import resource.ResourceType;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Rozhranie reprezentujúce materiál alebo objekt, ktorý má životy (HP) a môže byť zničený.
 */
public interface Material {

    /**
     * Skontroluje, či je materiál zničený (napríklad jeho životy sú 0 alebo menej).
     *
     * @return {@code true} ak je materiál zničený, inak {@code false}.
     */
    boolean isDestroyed();

    /**
     * Zmení aktuálne životy materiálu o danú hodnotu (môže byť kladná alebo záporná).
     *
     * @param hp Hodnota, o ktorú sa zmenia životy (môže byť napr. poškodenie alebo liečenie).
     */
    ResourceType changeHpBy(int hp);
    Rectangle getBounds() ;

    void draw(Graphics g);
}
