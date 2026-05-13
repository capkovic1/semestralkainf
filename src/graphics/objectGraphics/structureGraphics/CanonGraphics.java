package graphics.objectGraphics.structureGraphics;

import object.structure.Canon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Trieda zodpovedná za vykresľovanie grafiky kanónu na hraciu plochu.
 * Vykresľuje základné tvary kanónu, kovové platne, šrouby a hlaveň
 * s podporou rotácie a cachovania textúr pre zvýšenie výkonu.
 */
public class CanonGraphics {

    /** Veľkosť kanónu v pixeloch (40x40). */
    private static final int SIZE = 40;

    /** Cache pre uloženie generovaných elips (šroubov) pre jednotlivé pozície. */
    private static final HashMap<Point, List<Ellipse2D>> METALCACHE = new HashMap<>();

    /** Cache pre uloženie generovaných platní pre jednotlivé pozície. */
    private static final HashMap<Point, List<Rectangle2D>> PLATECACHE = new HashMap<>();

    private Canon canon;

    public CanonGraphics(Canon canon) {
        this.canon = canon;
    }
    /**
     * Vykreslí kanón na zadané súradnice s daným uhlom rotácie.
     *
     * @param g Grafický kontext, do ktorého sa kanón vykreslí.
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        int startX = this.canon.getX() * 20; // Prepočet na základy mriežky (pixelová pozícia)
        int startY = this.canon.getY() * 20;

        // Uloženie pôvodnej transformácie
        AffineTransform oldTransform = g2d.getTransform();

        // Nastavenie rotácie okolo stredu kanónu
        g2d.translate(startX + SIZE / 2, startY + SIZE / 2);
        g2d.rotate(Math.toRadians(this.canon.getAngle()));
        g2d.translate(-SIZE / 2, -SIZE / 2);

        // Generovanie a získanie textúr z cache
        Point key = new Point(canon.getX(), canon.getY());
        List<Ellipse2D> bolts = METALCACHE.computeIfAbsent(key, k -> this.generateBolts(startX, startY));
        List<Rectangle2D> plates = PLATECACHE.computeIfAbsent(key, k -> this.generatePlates(startX, startY));

        // Hlavné telo kanónu (tmavá oceľ)
        g2d.setColor(new Color(80, 80, 80));
        g2d.fillRect(5, 10, 30, 20);

        // Kovové platne
        g2d.setColor(new Color(100, 100, 100));
        for (Rectangle2D plate : plates) {
            g2d.fill(plate);
        }

        // Šrouby
        Random rand = new Random(this.canon.getX() * 1000L + this.canon.getY() * 500L);
        for (Ellipse2D bolt : bolts) {
            int grayValue = 150 + rand.nextInt(30);
            g2d.setColor(new Color(grayValue, grayValue, grayValue));
            g2d.fill(bolt);
        }

        // Hlaveň kanónu
        g2d.setColor(new Color(60, 60, 60));
        g2d.fillRect(35, 18, 15, 4);

        // Obrysy kanónu
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(5, 10, 30, 20);
        g2d.drawLine(35, 18, 50, 18);
        g2d.drawLine(35, 22, 50, 22);

        // Obnovenie pôvodnej transformácie a uvoľnenie zdrojov
        g2d.setTransform(oldTransform);
        g2d.dispose();
    }

    /**
     * Vygeneruje náhodné elipsy predstavujúce šrouby na tele kanónu.
     * Pozície sú generované na základe súradníc, aby boli konzistentné.
     *
     * @param startX Pixelová X pozícia kanónu.
     * @param startY Pixelová Y pozícia kanónu.
     * @return Zoznam elips reprezentujúcich šrouby.
     */
    private List<Ellipse2D> generateBolts(int startX, int startY) {
        List<Ellipse2D> bolts = new ArrayList<>();
        Random rand = new Random(startX * 1000L + startY * 500L);

        for (int i = 0; i < 8; i++) {
            int boltSize = rand.nextInt(4) + 3;
            int boltX = startX + 5 + rand.nextInt(30 - boltSize);
            int boltY = startY + 10 + rand.nextInt(20 - boltSize);
            bolts.add(new Ellipse2D.Double(boltX, boltY, boltSize, boltSize));
        }

        return bolts;
    }

    /**
     * Vygeneruje kovové platne na tele kanónu.
     * Pozície sú preddefinované, aby vytvorili vizuálne platne.
     *
     * @param startX Pixelová X pozícia kanónu.
     * @param startY Pixelová Y pozícia kanónu.
     * @return Zoznam obdĺžnikov reprezentujúcich platne.
     */
    private List<Rectangle2D> generatePlates(int startX, int startY) {
        List<Rectangle2D> plates = new ArrayList<>();

        // Vertikálne platne
        plates.add(new Rectangle2D.Double(startX + 10, startY + 10, 3, 20));
        plates.add(new Rectangle2D.Double(startX + 25, startY + 10, 3, 20));

        // Horizontálne platne
        plates.add(new Rectangle2D.Double(startX + 5, startY + 15, 30, 2));
        plates.add(new Rectangle2D.Double(startX + 5, startY + 20, 30, 2));

        return plates;
    }
}
