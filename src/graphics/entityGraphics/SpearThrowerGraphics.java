package graphics.entityGraphics;

import entities.enemies.SpearThrower;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Táto trieda bola vygenerovaná AI
 *
 * Trieda {@code SpearThrowerGraphics} zabezpečuje vykresľovanie grafiky
 * pre entitu SpearThrower (vrhač oštepov).
 * Grafika obsahuje telo, oči, pás a ruku vrhača,
 * pričom zohľadňuje pozíciu a uhol natočenia entity.
 */
public class SpearThrowerGraphics implements EnemyGraphics {
    /** Referencia na entitu SpearThrower, ktorá sa kreslí */
    private final SpearThrower spearThrower;

    /** Farba pokožky vrhača */
    private final Color skinColor = new Color(210, 180, 140);
    /** Farba pásu vrhača */
    private final Color loinclothColor = new Color(70, 40, 20);
    /** Farba očí */
    private final Color eyeColor = Color.BLACK;

    /**
     * Konštruktor, ktorý nastaví vrhača, ktorého grafiku treba vykresliť.
     *
     * @param spearThrower entita SpearThrower
     */
    public SpearThrowerGraphics(SpearThrower spearThrower) {
        this.spearThrower = spearThrower;
    }

    /**
     * Vykreslí efekt smrti - tmavý červený ovál predstavujúci krv pod vrhačom.
     *
     * @param g grafický kontext, do ktorého sa kreslí efekt
     */
    public void drawDeathEffect(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(80, 40, 40, 150)); // Tmavočervený polopriehľadný efekt krvi
        g2d.fillOval(this.spearThrower.getX(), this.spearThrower.getY() + 20, 50, 20);
    }

    /**
     * Vykreslí celú postavu vrhača s rotáciou podľa aktuálneho uhla natočenia.
     *
     * @param g grafický kontext, do ktorého sa kreslí
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();

        // Určí stred rotácie - stred postavy
        int centerX = this.spearThrower.getX() + 25;
        int centerY = this.spearThrower.getY() + 25;

        // Posunutie súradníc do stredu a rotácia o uhol vrhača
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(this.spearThrower.getAngle()));
        g2d.translate(-centerX, -centerY);

        // Vykreslenie jednotlivých častí postavy
        this.drawBody(g2d);
        this.drawEyes(g2d);
        this.drawLoincloth(g2d);
        this.drawArm(g2d);

        g2d.dispose();
    }

    /**
     * Vykreslí hlavu a trup vrhača.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawBody(Graphics2D g2d) {
        // Hlava ako svetlohnedý ovál
        g2d.setColor(this.skinColor);
        g2d.fillOval(this.spearThrower.getX() + 15, this.spearThrower.getY(), 20, 20);

        // Trup ako obdĺžnik
        g2d.fillRect(this.spearThrower.getX() + 20, this.spearThrower.getY() + 20, 10, 20);
    }

    /**
     * Vykreslí oči ako malé čierne ovály na hlave.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawEyes(Graphics2D g2d) {
        g2d.setColor(this.eyeColor);
        g2d.fillOval(this.spearThrower.getX() + 20, this.spearThrower.getY() + 8, 3, 3);
        g2d.fillOval(this.spearThrower.getX() + 27, this.spearThrower.getY() + 8, 3, 3);
    }

    /**
     * Vykreslí pás ako trojuholník tmavohnedej farby na spodnej časti tela.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawLoincloth(Graphics2D g2d) {
        g2d.setColor(this.loinclothColor);
        Polygon loincloth = new Polygon(
                new int[]{this.spearThrower.getX() + 15, this.spearThrower.getX() + 35, this.spearThrower.getX() + 25},
                new int[]{this.spearThrower.getY() + 40, this.spearThrower.getY() + 40, this.spearThrower.getY() + 30},
                3
        );
        g2d.fillPolygon(loincloth);
    }

    /**
     * Vykreslí vrhačovu ruku, ktorá je predĺžená pre efekt vrhania oštepu.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawArm(Graphics2D g2d) {
        g2d.setColor(this.skinColor);
        // Ruka na vrhanie - obdĺžnik predĺžený smerom von
        g2d.fillRect(this.spearThrower.getX() + 30, this.spearThrower.getY() + 20, 20, 5);
    }
}
