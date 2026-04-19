package graphics.ruka;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * Trieda ArrowGraphics zodpovedá za vykreslenie grafickej reprezentácie šípu.
 * Umožňuje vykresliť šíp s danou pozíciou, uhlom natočenia a dĺžkou.
 */
public class ArrowGraphics {
    /**
     * Vykreslí šíp na zadaný grafický kontext.
     * Šíp je vykreslený ako čiara s trojuholníkovou šípkou a malým obdĺžnikom na násade.
     * Pozícia, uhol natočenia a dĺžka šípu sú parametre metódy.
     *
     * @param g2d Grafický kontext, na ktorý sa šíp vykreslí
     * @param x X súradnica začiatku šípu
     * @param y Y súradnica začiatku šípu
     * @param angle Uhol natočenia šípu v stupňoch (0 stupňov je horizontálne doprava)
     * @param length Dĺžka hlavnej čiary šípu v pixeloch
     */
    public void draw(Graphics2D g2d, int x, int y, double angle, int length) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(angle));
        g2d.translate(-x, -y);

        g2d.setColor(new Color(180, 160, 140));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y, x + length, y);

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(x + length, y);
        arrowHead.addPoint(x + length + 10, y - 5);
        arrowHead.addPoint(x + length + 10, y + 5);
        g2d.setColor(Color.GRAY);
        g2d.fill(arrowHead);

        g2d.setColor(new Color(200, 50, 50));
        g2d.fillRect(x + 5, y - 4, 12, 8);

        g2d.setColor(Color.WHITE);
        g2d.drawLine(x + 5, y - 4, x + 17, y - 4);
        g2d.drawLine(x + 5, y + 4, x + 17, y + 4);

        g2d.setTransform(oldTransform);
    }
}
