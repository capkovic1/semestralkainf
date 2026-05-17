package graphics.gameGraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Táto trieda bola vygenerovaná AI
 *
 * Trieda zodpovedná za vykresľovanie prázdnych buniek hernej mapy.
 * Vykresľuje základnú trávnatú plochu so štruktúrovanými lístkami trávy.
 * Používa cachovanie generovaných trávových listov pre dané bunky, aby
 * zabezpečila konzistentný vzhľad a lepší výkon.
 */
public class EmptyCellGraphics {

    /** Veľkosť jednej bunky v pixeloch (30x30). */
    private static final int SIZE = 30;

    /** Cache, ktorá ukladá generované trávy (polygóny) pre jednotlivé bunky podľa ich pozície. */
    private static final Map<Point, List<Polygon>> GRASSCACHE = new HashMap<>();

    /**
     * Vykreslí prázdnu bunku na pozíciu (x, y) v mriežke.
     * Bunka je vyplnená zelenou farbou a obsahuje grafiku trávy.
     *
     * @param g Grafický kontext, do ktorého sa bude kresliť.
     * @param x X súradnica bunky v mriežke.
     * @param y Y súradnica bunky v mriežke.
     */
    public void getEmptyCellGraphics(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D)g.create();
        int startX = x * SIZE;
        int startY = y * SIZE;

        // Vyplnenie pozadia bunky trávnatou farbou
        g2d.setColor(new Color(70, 133, 70));
        g2d.fillRect(startX, startY, SIZE, SIZE);

        // Získanie alebo vygenerovanie polygónov trávy pre danú bunku
        List<Polygon> grassBlades = GRASSCACHE.computeIfAbsent(new Point(x, y), _ -> generateGrass(startX, startY));

        // Vykreslenie trávy tmavšou zelenou farbou
        g2d.setColor(new Color(52, 117, 52));
        for (Polygon blade : grassBlades) {
            g2d.fill(blade);
        }

        g2d.dispose();
    }

    /**
     * Generuje zoznam polygónov reprezentujúcich lístky trávy pre danú bunku.
     * Používa pevne daný seed pre generátor náhodných čísel, aby bola tráva
     * konzistentná pre danú pozíciu bunky.
     *
     * @param startX Pixelová X pozícia bunky.
     * @param startY Pixelová Y pozícia bunky.
     * @return Zoznam polygonov predstavujúcich trávu.
     */
    private static List<Polygon> generateGrass(int startX, int startY) {
        List<Polygon> grassBlades = new ArrayList<>();
        Random rand = new Random(startX * 2000L + startY * 1000L);

        for (int i = 0; i < 8; i++) {
            int baseX = startX + rand.nextInt(SIZE - 5);
            int baseY = startY + rand.nextInt(SIZE - 5) + 20;

            int[] xPoints = { baseX, baseX + 3, baseX + 6 };
            int[] yPoints = { baseY, baseY - rand.nextInt(10) - 5, baseY };
            grassBlades.add(new Polygon(xPoints, yPoints, 3));
        }
        return grassBlades;
    }
}

