package graphics.entityGraphics;

import entities.enemies.Wolf;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Táto trieda bola vygenerovaná AI
 * Trieda {@code WolfGraphics} zabezpečuje vykresľovanie grafiky
 * pre entitu Wolf (vlka).
 * Grafika obsahuje telo, oči, tesáky a uši vlka,
 * pričom zohľadňuje pozíciu a uhol natočenia entity.
 */
public class WolfGraphics implements  EnemyGraphics {
    /** Referencia na entitu vlka, ktorého grafiku kreslíme */
    private final Wolf wolf;

    /** Farba srsti vlka - tmavosivá */
    private final Color furColor = new Color(50, 50, 50);
    /** Farba očí - žltá */
    private final Color eyeColor = new Color(255, 204, 0);
    /** Farba tesákov - biela */
    private final Color fangColor = Color.WHITE;

    /**
     * Konštruktor, ktorý nastaví vlka, ktorého grafiku treba vykresliť.
     *
     * @param wolf entita vlka
     */
    public WolfGraphics(Wolf wolf) {
        this.wolf = wolf;
    }

    /**
     * Vykreslí efekt smrti - tmavočervený ovál predstavujúci krv pod vlkom.
     *
     * @param g grafický kontext, do ktorého sa kreslí efekt
     */
    public void drawDeathEffect(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(80, 40, 40, 150)); // Tmavočervený polopriehľadný efekt krvi
        g2d.fillOval(this.wolf.getX(), this.wolf.getY() + 20, 50, 20);
    }

    /**
     * Vykreslí celého vlka s rotáciou podľa aktuálneho uhla natočenia.
     *
     * @param g grafický kontext, do ktorého sa kreslí
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();

        // Určí stred rotácie - stred tela vlka
        int centerX = this.wolf.getX() + 25;
        int centerY = this.wolf.getY() + 25;

        // Posunutie súradníc do stredu a rotácia o uhol vlka
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(this.wolf.getAngle()));
        g2d.translate(-centerX, -centerY);

        // Vykreslenie jednotlivých častí vlka
        this.drawBody(g2d);
        this.drawEyes(g2d);
        this.drawFangs(g2d);
        this.drawEars(g2d);

        g2d.dispose();
    }

    /**
     * Vykreslí telo vlka ako veľký tmavosivý ovál.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawBody(Graphics2D g2d) {
        g2d.setColor(this.furColor);
        g2d.fillOval(this.wolf.getX(), this.wolf.getY(), 50, 50);
    }

    /**
     * Vykreslí oči vlka - žlté s čiernymi zornicami.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawEyes(Graphics2D g2d) {
        g2d.setColor(this.eyeColor);
        g2d.fillOval(this.wolf.getX() + 15, this.wolf.getY() + 15, 6, 6);
        g2d.fillOval(this.wolf.getX() + 30, this.wolf.getY() + 15, 6, 6);

        g2d.setColor(Color.BLACK);
        g2d.fillOval(this.wolf.getX() + 17, this.wolf.getY() + 17, 2, 2);
        g2d.fillOval(this.wolf.getX() + 32, this.wolf.getY() + 17, 2, 2);
    }

    /**
     * Vykreslí biele tesáky vlka ako dva malé obdĺžniky pod očami.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawFangs(Graphics2D g2d) {
        g2d.setColor(this.fangColor);
        g2d.fillRect(this.wolf.getX() + 22, this.wolf.getY() + 33, 2, 6);
        g2d.fillRect(this.wolf.getX() + 28, this.wolf.getY() + 33, 2, 6);
    }

    /**
     * Vykreslí uši vlka ako dva trojuholníky zhodnej farby so srsťou.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawEars(Graphics2D g2d) {
        g2d.setColor(this.furColor);
        Polygon leftEar = new Polygon(
                new int[]{this.wolf.getX() + 10, this.wolf.getX() + 15, this.wolf.getX() + 5},
                new int[]{this.wolf.getY() + 5, this.wolf.getY() + 20, this.wolf.getY() + 20},
                3
        );

        Polygon rightEar = new Polygon(
                new int[]{this.wolf.getX() + 40, this.wolf.getX() + 45, this.wolf.getX() + 35},
                new int[]{this.wolf.getY() + 5, this.wolf.getY() + 20, this.wolf.getY() + 20},
                3
        );

        g2d.fillPolygon(leftEar);
        g2d.fillPolygon(rightEar);
    }
}


