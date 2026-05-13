package graphics.entityGraphics;

import entities.enemies.Frog;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Táto trieda bola vygenerovaná AI
 * Trieda {@code FrogGraphics} zabezpečuje vykresľovanie grafiky žaby.
 * Grafika je založená na súradniciach a uhloch žaby a vykresľuje jej telo,
 * oči, brucho, nohy a efekty smrti.
 */
public class FrogGraphics implements  EnemyGraphics {
    /** Referencia na žabu, ktorej grafiku kreslíme */
    private final Frog frog;

    /** Farba tela žaby */
    private final Color bodyColor = new Color(50, 150, 50);
    /** Farba očí */
    private final Color eyeColor = new Color(255, 255, 255);
    /** Farba dúhovky (pupil) */
    private final Color pupilColor = Color.BLACK;
    /** Farba brucha */
    private final Color bellyColor = new Color(200, 255, 200);

    /**
     * Konštruktor vytvára grafiku pre danú žabu.
     *
     * @param frog žaba, ktorej grafiku budeme vykresľovať
     */
    public FrogGraphics(Frog frog) {
        this.frog = frog;
    }

    /**
     * Vykreslí efekt smrti žaby ako tmavozelený priehľadný ovál pod žabou.
     *
     * @param g grafický kontext, do ktorého sa kreslí
     */
    public void drawDeathEffect(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(0, 80, 0, 150)); // tmavozelený priehľadný efekt smrti
        g2d.fillOval(this.frog.getX(), this.frog.getY() + 20, 50, 20);
    }

    /**
     * Vykreslí celú žabu vrátane tela, očí, brucha a nôh.
     * Žaba sa vykreslí s rotáciou podľa jej uhla natočenia.
     *
     * @param g grafický kontext, do ktorého sa kreslí
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();

        // Vypočítame stred žaby pre rotáciu
        int centerX = this.frog.getX() + 25;
        int centerY = this.frog.getY() + 25;

        // Posunieme súradnice do stredu a otočíme o uhol žaby
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(this.frog.getAngle()));
        g2d.translate(-centerX, -centerY);

        // Vykreslíme jednotlivé časti žaby
        this.drawBody(g2d);
        this.drawEyes(g2d);
        this.drawBelly(g2d);
        this.drawLegs(g2d);

        g2d.dispose();
    }

    /**
     * Vykreslí telo žaby ako plochý zelený ovál.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawBody(Graphics2D g2d) {
        g2d.setColor(this.bodyColor);
        g2d.fillOval(this.frog.getX(), this.frog.getY(), 50, 40);
    }

    /**
     * Vykreslí oči žaby - biele ovály s čiernymi dúhovkami.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawEyes(Graphics2D g2d) {
        // Ľavé oko
        g2d.setColor(this.eyeColor);
        g2d.fillOval(this.frog.getX() + 10, this.frog.getY() + 5, 10, 10);
        g2d.setColor(this.pupilColor);
        g2d.fillOval(this.frog.getX() + 13, this.frog.getY() + 8, 4, 4);

        // Pravé oko
        g2d.setColor(this.eyeColor);
        g2d.fillOval(this.frog.getX() + 30, this.frog.getY() + 5, 10, 10);
        g2d.setColor(this.pupilColor);
        g2d.fillOval(this.frog.getX() + 33, this.frog.getY() + 8, 4, 4);
    }

    /**
     * Vykreslí brucho žaby ako svetlozelený ovál uprostred tela.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawBelly(Graphics2D g2d) {
        g2d.setColor(this.bellyColor);
        g2d.fillOval(this.frog.getX() + 10, this.frog.getY() + 15, 30, 20);
    }

    /**
     * Vykreslí nohy žaby - dve oválne a dve trojuholníkové nohy.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawLegs(Graphics2D g2d) {
        g2d.setColor(this.bodyColor);

        // Predné oválne nohy
        g2d.fillOval(this.frog.getX() + 5, this.frog.getY() + 25, 10, 5);
        g2d.fillOval(this.frog.getX() + 35, this.frog.getY() + 25, 10, 5);

        // Zadné trojuholníkové nohy (ľavá a pravá)
        Polygon leftLeg = new Polygon(
                new int[]{this.frog.getX(), this.frog.getX() + 5, this.frog.getX() + 10},
                new int[]{this.frog.getY() + 35, this.frog.getY() + 45, this.frog.getY() + 40},
                3
        );

        Polygon rightLeg = new Polygon(
                new int[]{this.frog.getX() + 40, this.frog.getX() + 45, this.frog.getX() + 50},
                new int[]{this.frog.getY() + 35, this.frog.getY() + 45, this.frog.getY() + 40},
                3
        );

        g2d.fillPolygon(leftLeg);
        g2d.fillPolygon(rightLeg);
    }
}
