package graphics.weaponGraphics;

import entities.player.Player;

import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.geom.AffineTransform;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Táto trieda bola vygenerovaná AI
 *
 * Trieda HammerGraphics reprezentuje grafické zobrazenie kladiva v ruke hráča.
 * Implementuje animáciu švihu kladiva pomocou časovača a otáčania grafiky.
 * Trieda implementuje rozhranie HandGraphics.
 */
public class HammerGraphics implements HandGraphics {

    /**
     * Aktuálny uhol natočenia kladiva počas švihu.
     */
    private int swingAngle = 0;

    /**
     * Indikuje, či práve prebieha animácia švihu.
     */
    private boolean swinging = false;

    /**
     * Časovač riadiaci animáciu švihu.
     */
    private final Timer swingTimer;

    /**
     * Krok animácie švihu.
     */
    private int swingStep = 0;

    /**
     * Konštruktor triedy HammerGraphics.
     */
    public HammerGraphics() {
        this.swingTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (HammerGraphics.this.swingStep < 5) {
                    HammerGraphics.this.swingAngle = -30 + (HammerGraphics.this.swingStep * 15);
                } else if (HammerGraphics.this.swingStep < 10) {
                    HammerGraphics.this.swingAngle = 30 - ((HammerGraphics.this.swingStep - 5) * 15);
                } else {
                    HammerGraphics.this.swingAngle = 0;
                    HammerGraphics.this.swingTimer.stop();
                    HammerGraphics.this.swinging = false;
                }
                HammerGraphics.this.swingStep++;
                repaintAllFrames();
            }
        });
    }

    /**
     * Vykreslí kladivo a ruky hráča, pričom kladivo sa počas animácie natáča podľa uhla švihu.
     *
     * @param g2d Grafický kontext na kreslenie
     * @param player Inštancia hráča, podľa ktorej sa určuje pozícia
     * @param armOffset Posunutie paží (môže slúžiť na ďalšie animácie alebo efekty)
     */
    @Override
    public void draw(Graphics2D g2d, Player player, int armOffset) {
        int centerX = player.getX() + 15;
        int centerY = player.getY() + 10 + armOffset;

        int leftHandX = centerX - 20;
        int rightHandX = centerX + 20;

        int hammerHandleWidth = 70;
        int hammerHandleHeight = 5;
        int hammerHeadWidth = 20;
        int hammerHeadHeight = 40;

        AffineTransform oldTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(this.swingAngle), centerX, centerY);

        g2d.setColor(new Color(184, 135, 97));
        g2d.fillOval(leftHandX, centerY, 20, 20);
        g2d.fillOval(rightHandX, centerY, 20, 20);

        g2d.setColor(new Color(139, 69, 19));
        g2d.fillRect(centerX, centerY, hammerHandleWidth, hammerHandleHeight);

        g2d.setColor(new Color(169, 169, 169));
        g2d.fillRect(centerX + hammerHandleWidth, centerY - hammerHeadWidth / 2, hammerHeadWidth, hammerHeadHeight);

        g2d.setTransform(oldTransform);
    }

    /**
     * Spustí animáciu švihu kladiva, ak ešte neprebieha.
     */
    @Override
    public void use() {
        if (!this.swinging) {
            this.swinging = true;
            this.swingStep = 0;
            this.swingTimer.start();
        }
    }

    /**
     * Triggeruje repaint všetkých JFrame a JPanel komponentov.
     * Používa se na prekreslenie scény počas animácie.
     */
    private static void repaintAllFrames() {
        SwingUtilities.invokeLater(() -> {
            for (var frame : javax.swing.JFrame.getFrames()) {
                frame.repaint();
            }
        });
    }
}

