package object.structure;

import entities.enemies.Enemy;
import graphics.objectGraphics.structureGraphics.WallGraphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

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
     * Priebežná aktualizácia steny - momentálne bez správania.
     *
     * @param enemies zoznam nepriateľov (nevyužité)
     */
    @Override
    public void update(ArrayList<Enemy> enemies) {

    }

    /**
     * Indikuje, či je stena zničená.
     *
     * @return {@code true} ak je zničená (momentálne vždy {@code false})
     */
    @Override
    public boolean isDestroyed() {
        return false;
    }

    /**
     * Vráti cenu postavenia steny v kameňoch.
     *
     * @return cena steny
     */
    @Override
    public int getPrice() {
        return 50;
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
    /**
     * Vykreslí stenu pomocí grafického objektu {@link WallGraphics}.
     *
     * @param g grafický kontext pro vykreslení
     */
    @Override
    public void draw(Graphics g) {
        new WallGraphics(this).draw(g);
    }
    /**
     * Vráti ohraničujúci obdĺžnik steny pro kolízie.
     *
     * @return ohraničujúci {@link Rectangle}
     */
    public Rectangle getBounds() {
        return new Rectangle(this.x - 10, this.y - 10, 20, 20);
    }
}
