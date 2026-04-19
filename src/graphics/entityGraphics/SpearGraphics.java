package graphics.entityGraphics;

import projectile.Spear;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Táto trieda bola vygenerovaná AI
 * Trieda {@code SpearGraphics} zabezpečuje vykresľovanie grafiky oštepu.
 * Oštep je vykresľovaný podľa jeho pozície a uhla natočenia.
 */
public class SpearGraphics {
    /** Referencia na oštep, ktorý sa má vykresliť */
    private final Spear spear;

    /** Farba násady oštepu */
    private final Color shaftColor = new Color(160, 120, 60);
    /** Farba hrotu oštepu */
    private final Color tipColor = new Color(180, 180, 180);

    /**
     * Konštruktor vytvára grafiku pre daný oštep.
     *
     * @param spear oštep, ktorý sa má vykresliť
     */
    public SpearGraphics(Spear spear) {
        this.spear = spear;
    }

    /**
     * Vykreslí celý oštep vrátane násady a hrotu s rotáciou podľa uhla oštepu.
     *
     * @param g grafický kontext, do ktorého sa kreslí
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();

        // Stred rotácie - približne stred násady oštepu
        int centerX = this.spear.getX() + 20;
        int centerY = this.spear.getY() + 2;

        // Posunutie súradníc do stredu a rotácia o uhol oštepu
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(this.spear.getAngle()));
        g2d.translate(-centerX, -centerY);

        // Vykreslí násadu a hrot oštepu
        this.drawShaft(g2d);
        this.drawTip(g2d);

        g2d.dispose();
    }

    /**
     * Vykreslí násadu oštepu ako hnedý obdĺžnik.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawShaft(Graphics2D g2d) {
        g2d.setColor(this.shaftColor);
        g2d.fillRect(this.spear.getX(), this.spear.getY(), 40, 4);
    }

    /**
     * Vykreslí hrot oštepu ako šedý trojuholník na konci násady.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawTip(Graphics2D g2d) {
        g2d.setColor(this.tipColor);
        Polygon tip = new Polygon(
                new int[]{this.spear.getX() + 40, this.spear.getX() + 45, this.spear.getX() + 40},
                new int[]{this.spear.getY() + 2, this.spear.getY() + 4, this.spear.getY() + 6},
                3
        );
        g2d.fillPolygon(tip);
    }
}
