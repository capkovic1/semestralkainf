package graphics.handGraphics;

import entities.player.Player;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;

import java.awt.geom.AffineTransform;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Trieda reprezentujúca grafiku meča drženého hráčom.
 * Zabezpečuje kreslenie meča a animáciu jeho švihu.
 */
public class SwordGraphics implements HandGraphics {

    /** Aktuálny uhol švihu meča v stupňoch. */
    private int swingAngle = 0;

    /** Indikuje, či je momentálne meč v pohybe (švihu). */
    private boolean swinging = false;

    /** Timer na riadenie animácie švihu meča. */
    private final Timer swingTimer;

    /** Počet krokov animácie švihu. */
    private int swingStep = 0;

    /**
     * Vytvorí novú inštanciu SwordGraphics.
     *
     * @param repaintCallback Callback, ktorý sa zavolá po každom kroku animácie
     *                        na prekreslenie komponentu.
     */
    public SwordGraphics(Runnable repaintCallback) {
        this.swingTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SwordGraphics.this.swingStep < 5) {
                    SwordGraphics.this.swingAngle = -150 + (SwordGraphics.this.swingStep * 36);
                } else if (SwordGraphics.this.swingStep < 10) {
                    SwordGraphics.this.swingAngle = 20 - ((SwordGraphics.this.swingStep - 5) * 36);
                } else {
                    SwordGraphics.this.swingAngle = 0;
                    SwordGraphics.this.swingTimer.stop();
                    SwordGraphics.this.swinging = false;
                }
                SwordGraphics.this.swingStep++;
                repaintCallback.run();
            }
        });
    }

    /**
     * Vykreslí meč na základe pozície hráča, posunu ramena a aktuálneho uhla švihu.
     *
     * @param g2d       Grafický kontext na kreslenie.
     * @param player    Inštancia hráča, ktorej pozícia a orientácia určuje pozíciu meča.
     * @param armOffset Posun ramena pre animácie.
     */
    @Override
    public void drawGraphics(Graphics2D g2d, Player player, int armOffset) {
        int centerX = player.getX() + 15;
        int centerY = player.getY() + 10 + armOffset;

        int leftHandX = centerX - 20;
        int rightHandX = centerX + 20;

        int handleWidth = 70;
        int handleHeight = 5;
        int bladeWidth = 80;
        int bladeHeight = 20;

        AffineTransform oldTransform = g2d.getTransform();

        g2d.rotate(Math.toRadians(this.swingAngle), centerX, centerY);

        // Ruky držania meča
        g2d.setColor(new Color(184, 135, 97));
        g2d.fillOval(leftHandX, centerY, 20, 20);
        g2d.fillOval(rightHandX, centerY, 20, 20);

        // Rukoväť meča
        g2d.setColor(new Color(139, 69, 19));
        g2d.fillRect(centerX, centerY, handleWidth, handleHeight);

        // Čepeľ meča
        g2d.setColor(new Color(169, 169, 169));
        g2d.fillRect(centerX + handleWidth, centerY, bladeWidth, bladeHeight);

        // Hrot čepele (trojuholník)
        int[] xPoints = { centerX + handleWidth + bladeWidth, centerX + handleWidth + bladeWidth, centerX + handleWidth + bladeWidth + 10 };
        int[] yPoints = { centerY, centerY + bladeHeight, centerY + bladeHeight / 2 };

        Polygon blade = new Polygon(xPoints, yPoints, 3);
        g2d.fillPolygon(blade);

        g2d.setTransform(oldTransform);
    }

    /**
     * Spustí animáciu švihu meča, ak práve neprebieha.
     */
    @Override
    public void use() {
        if (!this.swinging) {
            this.swinging = true;
            this.swingStep = 0;
            this.swingTimer.start();
        }
    }
}

