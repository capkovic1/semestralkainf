package graphics.entityGraphics;

import entity.Zombie;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;

/**
 * Táto trieda bola vygenerovaná AI
 * Trieda {@code ZombieGraphics} zabezpečuje vykresľovanie grafiky
 * pre entitu Zombie (zombie).
 * Grafika zobrazuje telo, oči, rany a ústa zombie,
 * pričom zohľadňuje pozíciu a uhol natočenia entity.
 */
public class ZombieGraphics implements EnemyGraphics {
    /** Referencia na entitu zombie, ktorej grafiku kreslíme */
    private final Zombie zombie;

    /** Farba pokožky zombie - tmavozelená */
    private final Color skinColor = new Color(38, 87, 39);
    /** Farba rán - tmavohnedá */
    private final Color woundColor = new Color(120, 50, 50);
    /** Farba očí - jasne červená */
    private final Color eyeColor = new Color(255, 50, 50);

    /**
     * Konštruktor, ktorý nastaví zombie, ktorej grafiku treba vykresliť.
     *
     * @param zombie entita zombie
     */
    public ZombieGraphics(Zombie zombie) {
        this.zombie = zombie;
    }

    /**
     * Vykreslí efekt smrti - tmavý polopriehľadný červený ovál predstavujúci krv pod zombie.
     *
     * @param g grafický kontext, do ktorého sa kreslí efekt
     */
    public void drawDeathEffect(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(100, 0, 0, 150)); // Polopriehľadná tmavočervená krv
        g2d.fillOval(this.zombie.getX(), this.zombie.getY(), 50, 20);
    }

    /**
     * Vykreslí celú zombie so správnym natočením podľa uhla entity.
     *
     * @param g grafický kontext, do ktorého sa kreslí
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();

        // Určí stred rotácie (stred tela zombie)
        int centerX = this.zombie.getX() + 25;
        int centerY = this.zombie.getY() + 25;

        // Posunutie do stredu a rotácia o aktuálny uhol zombie
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(this.zombie.getAngle()));
        g2d.translate(-centerX, -centerY);

        // Vykreslenie jednotlivých častí zombie
        this.drawBody(g2d);
        this.drawWounds(g2d);
        this.drawEyes(g2d);
        this.drawMouth(g2d);

        g2d.dispose();
    }

    /**
     * Vykreslí telo zombie ako zelený ovál s rozšírenými ramenami.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawBody(Graphics2D g2d) {
        g2d.setColor(this.skinColor);
        g2d.fillOval(this.zombie.getX(), this.zombie.getY(), 50, 50);

        // Ramená zombie (rozšírené línie na bokoch tela)
        g2d.setStroke(new BasicStroke(8));
        g2d.drawLine(this.zombie.getX() + 10, this.zombie.getY() + 20,
                this.zombie.getX() - 15, this.zombie.getY() + 10);
        g2d.drawLine(this.zombie.getX() + 40, this.zombie.getY() + 20,
                this.zombie.getX() + 65, this.zombie.getY() + 10);
    }

    /**
     * Vykreslí rany na tele zombie ako tmavohnedé ovály a čiary.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawWounds(Graphics2D g2d) {
        g2d.setColor(this.woundColor);

        // Dve rany - ovály
        g2d.fillOval(this.zombie.getX() + 15, this.zombie.getY() + 10, 8, 8);
        g2d.fillOval(this.zombie.getX() + 30, this.zombie.getY() + 25, 6, 6);

        // Čiara znázorňujúca trhlinu
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(this.zombie.getX() + 18, this.zombie.getY() + 18,
                this.zombie.getX() + 15, this.zombie.getY() + 25);
    }

    /**
     * Vykreslí oči zombie ako červené ovály s bielymi žiakmi.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawEyes(Graphics2D g2d) {
        g2d.setColor(this.eyeColor);
        g2d.fillOval(this.zombie.getX() + 15, this.zombie.getY() + 15, 8, 8);
        g2d.fillOval(this.zombie.getX() + 30, this.zombie.getY() + 15, 8, 8);

        g2d.setColor(Color.WHITE);
        g2d.fillOval(this.zombie.getX() + 17, this.zombie.getY() + 17, 3, 3);
        g2d.fillOval(this.zombie.getX() + 32, this.zombie.getY() + 17, 3, 3);
    }

    /**
     * Vykreslí ústa zombie ako čierny polkruh so zubami.
     *
     * @param g2d grafický 2D kontext
     */
    private void drawMouth(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillArc(this.zombie.getX() + 20, this.zombie.getY() + 30, 15, 10, 0, 180);

        g2d.setColor(Color.WHITE);
        // Zuby ako malé obdĺžničky v ústach
        for (int i = 0; i < 4; i++) {
            g2d.fillRect(this.zombie.getX() + 21 + i * 4, this.zombie.getY() + 30, 2, 3);
        }
    }
}
