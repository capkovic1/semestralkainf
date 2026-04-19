package graphics.objectGraphics;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.BasicStroke;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Trieda zodpovedná za vykresľovanie grafiky steny,
 * ktorá pozostáva z obdĺžnikového tvaru so štruktúrou kameňov (pebblov).
 */
public class WallGraphics {
    private static final int SIZE = 20;

    /**
     * Kešovanie pozícií a ich generovaných štruktúr kameňov,
     * aby sa zachovala konzistencia vzhľadu každej bunky steny.
     */
    private static final Map<Point, List<Ellipse2D>> STONECACHE = new HashMap<>();

    /**
     * Vykreslí grafiku steny na základe daných súradníc.
     * Vytvorí obdĺžnik so svetlosivým základom a bielym ohraničením,
     * a na ňom vykreslí náhodne rozmiestnené kamienky.
     *
     * @param g Grafický kontext na vykreslenie.
     * @param x X súradnica bunky steny (v jednotkách buniek, nie pixelov).
     * @param y Y súradnica bunky steny (v jednotkách buniek, nie pixelov).
     */
    public void getWallGrafics(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D)g.create();
        int startX = x * SIZE;
        int startY = y * SIZE;

        // Vykreslenie pozadia bunky
        g2d.setColor(new Color(129, 126, 126));
        g2d.fillRect(startX, startY, SIZE, SIZE);

        // Vykreslenie ohraničenia bunky
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(startX, startY, SIZE, SIZE);

        // Získanie alebo generovanie kamienkov pre danú pozíciu
        List<Ellipse2D> pebbles = STONECACHE.computeIfAbsent(new Point(x, y), k -> generatePebbles(startX, startY));

        // Vykreslenie kamienkov s náhodným odtieňom šedej
        Random rand = new Random(x * 1000L + y * 500L);
        for (Ellipse2D pebble : pebbles) {
            int grayValue = rand.nextInt(40) + 90;  // odtieň šedej 90-129
            g2d.setColor(new Color(grayValue, grayValue, grayValue));
            g2d.fill(pebble);
        }

        g2d.dispose();
    }

    /**
     * Generuje náhodné elipsy reprezentujúce kamienky v rámci bunky.
     * Každý kamienok má náhodnú veľkosť a polohu v rámci bunky.
     *
     * @param startX Počiatočná X súradnica bunky v pixeloch.
     * @param startY Počiatočná Y súradnica bunky v pixeloch.
     * @return Zoznam elips predstavujúcich kamienky v bunke.
     */
    private static List<Ellipse2D> generatePebbles(int startX, int startY) {
        List<Ellipse2D> pebbles = new ArrayList<>();
        Random rand = new Random(startX * 1000L + startY * 500L);

        for (int i = 0; i < 10; i++) {
            int pebbleSize = rand.nextInt(8) + 3;  // veľkosť kamienka 3-10 pixelov
            int pebbleX = startX + rand.nextInt(SIZE - pebbleSize);
            int pebbleY = startY + rand.nextInt(SIZE - pebbleSize);
            pebbles.add(new Ellipse2D.Double(pebbleX, pebbleY, pebbleSize, pebbleSize));
        }

        return pebbles;
    }
}

