package object.structure;

import entities.enemies.Enemy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Rozhranie reprezentujúce stavbu v hre (napríklad múr alebo kanón).
 * Implementácie musia poskytovať metódy pre aktualizáciu, vykreslenie,
 * kontrolu zničenia a informácie o cene a pozícii.
 */
public interface Structure {
    /**
     * Aktualizuje stav stavby (napr. kanón môže strieľať po nepriateľoch).
     *
     * @param enemies zoznam nepriateľov na mape
     */
    void update(ArrayList<Enemy> enemies);

    /**
     * Určuje, či je stavba zničená.
     *
     * @return {@code true} ak je zničená, inak {@code false}
     */
    boolean isDestroyed();

    /**
     * Vráti cenu stavby (napr. koľko kameňa stojí postaviť).
     *
     * @return cena stavby
     */
    int getPrice();

    /**
     * Vráti X súradnicu stavby (v mriežkových jednotkách).
     *
     * @return X súradnica
     */
    int getX();

    /**
     * Vráti Y súradnicu stavby (v mriežkových jednotkách).
     *
     * @return Y súradnica
     */
    int getY();

    /**
     * Vykreslí stavbu na danom grafickom kontexte.
     *
     * @param g grafický kontext
     */
    void draw(Graphics g);

    /**
     * Vráti ohraničujúci obdĺžnik stavby pre kolíznu kontrolu.
     *
     * @return ohraničujúci {@link Rectangle}
     */
    Rectangle getBounds();
}
