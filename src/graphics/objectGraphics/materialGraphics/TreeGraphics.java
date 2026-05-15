package graphics.objectGraphics.materialGraphics;

import object.material.Tree;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Trieda TreeGraphics zodpovedá za grafické znázornenie stromu.
 * Vykresľuje lístie stromu ako súbor elips rozmiestnených do kruhu.
 * Tiež uchováva entitu Wood, ktorá reprezentuje drevo stromu v hernej logike.
 */
public class TreeGraphics implements MaterialGraphics {

    /**
     * Polomer lístia stromu
     */
    private static final int SIZE = 30;

    /**
     * Zoznam elips reprezentujúcich lístie stromu
     */
    private final List<Ellipse2D> leaves;
    /**
     * Celkový tvar stromu zložený zo všetkých lístí
     */
    private final Area treeShape;

    /**
     * Konštruktor, ktorý vytvorí nový grafický strom na pozícii (x, y).
     * Vygeneruje lístie stromu v tvare kruhu.
     */
    public TreeGraphics(Tree tree) {
        this.leaves = new ArrayList<>();
        this.treeShape = new Area();

        int x = tree.getX();
        int y = tree.getY();

        this.generateLeaves(x, y);
    }

    /**
     * Vykreslí strom na daný grafický kontext.
     * Lístie je vyplnené zelenou farbou a obrys je čierny.
     *
     * @param g Grafický kontext, na ktorý sa strom vykreslí
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();

        g2d.setColor(new Color(77, 189, 77));
        g2d.fill(this.treeShape);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(this.treeShape);

        g2d.dispose();
    }

    /**
     * Vygeneruje lístie stromu ako sadu elips rozmiestnených rovnomerne okolo
     * kruhu so stredom v (startX, startY).
     *
     * @param startX X súradnica stredu lístia
     * @param startY Y súradnica stredu lístia
     */
    private void generateLeaves(int startX, int startY) {
        int numCircles = 6;
        double radius = SIZE;

        for (int i = 0; i < numCircles; i++) {
            double angle = 2 * Math.PI * i / numCircles;

            int circleX = (int)(startX + radius * Math.cos(angle) - radius / 2);
            int circleY = (int)(startY + radius * Math.sin(angle) - radius / 2);

            Ellipse2D leaf = new Ellipse2D.Double(circleX, circleY, radius * 2, radius * 2);
            this.leaves.add(leaf);
            this.treeShape.add(new Area(leaf));
        }
    }


}


