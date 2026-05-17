package graphics.entityGraphics;

import entities.player.Player;
import graphics.weaponGraphics.HandGraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Trieda zodpovedná za vykresľovanie hráča na obrazovku vrátane
 * rotácie hráča podľa uhla a kreslenia grafiky ruky (aktuálnej zbrane).
 */
public class PlayerGraphics {

    private final Player player;
    private HandGraphics handGraphics;

    /**
     * Konštruktor inicializuje grafiku hráča so základnou grafikou ruky.
     *
     * @param player Referencia na hráča, ktorého grafiku vykresľujeme.
     * @param handGraphics Grafika ruky (napr. zbrane), ktorá sa bude kresliť spolu s hráčom.
     */
    public PlayerGraphics(Player player, HandGraphics handGraphics) {
        this.player = player;
        this.handGraphics = handGraphics;
    }

    /**
     * Vykreslí hráča na plátno s aplikovanou rotáciou podľa uhla hráča.
     * Ruka (aktívna zbraň) sa vykreslí s použitím aktuálnej HandGraphics.
     *
     * @param g Grafický kontext, na ktorý sa hráč vykreslí.
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        double angle = this.player.getAngle() + 90;

        int centerX = this.player.getX() + 25;
        int centerY = this.player.getY() + 25;

        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(angle));
        g2d.translate(-centerX, -centerY);

        int armOffset = 0;
        this.handGraphics.draw(g2d, this.player, armOffset);

        g2d.setColor(new Color(238, 188, 156));
        g2d.fillOval(this.player.getX(), this.player.getY(), 50, 50);

        g2d.dispose();
    }

    /**
     * Nastaví grafiku ruky (aktuálnej zbrane), ktorá sa bude vykresľovať pri hráčovi.
     *
     * @param handGraphics Nová grafika ruky (zbrane).
     */
    public void setHandGraphics(HandGraphics handGraphics) {
        this.handGraphics = handGraphics;
    }

    /**
     * Získava aktuálnu grafiku ruky (zbrane), ktorá sa vykresľuje pri hráčovi.
     *
     * @return Aktuálna HandGraphics.
     */
    public HandGraphics getHandGraphics() {
        return this.handGraphics;
    }

}

