package graphics.projectilesGraphics;

import projectile.Arrow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;

/**
 * Táto trieda bola vygenerovaná AI
 *
 * Trieda ArrowGraphics zodpovedá za vykreslenie grafickej reprezentácie šípu.
 * Umožňuje vykresliť šíp s danou pozíciou, uhlom natočenia a dĺžkou.
 */
public class ArrowGraphics implements ProjectileGraphics {
    private final Arrow arrow;

    public  ArrowGraphics(Arrow arrow) {
        this.arrow = arrow;
    }
    /**
     * Vykreslí šíp na zadaný grafický kontext.
     * Šíp je vykreslený ako čiara s trojuholníkovou šípkou a malým obdĺžnikom na násade.
     * Pozícia, uhol natočenia a dĺžka šípu sú parametre metódy.
     *
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int x = (int)Math.round(this.arrow.getX());
        int y = (int)Math.round(this.arrow.getY());
        int length = 20;
        double angle = (int)Math.round(Math.toDegrees(Math.atan2(this.arrow.getDy(), this.arrow.getDx())));

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
