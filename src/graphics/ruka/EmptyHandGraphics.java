package graphics.ruka;

import entity.Player;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Trieda EmptyHandGraphics reprezentuje grafické zobrazenie voľných rúk hráča,
 * ktoré môžu animovať úder (punch). Implementuje rozhranie HandGraphics.
 * Animácia úderu je riadená časovačom, ktorý posúva pozíciu rúk počas úderu.
 */
public class EmptyHandGraphics implements HandGraphics {

    /**
     * Posunutie rúk pri údere v pixeloch.
     */
    private int punchOffset = 0;

    /**
     * Stav, či práve prebieha úder.
     */
    private boolean punching = false;

    /**
     * Časovač riadiaci animáciu úderu.
     */
    private final Timer punchTimer;

    /**
     * Krok animácie úderu.
     */
    private int punchStep = 0;

    /**
     * Konštruktor triedy EmptyHandGraphics.
     *
     * @param repaintCallback Callback funkcia, ktorá sa zavolá pri každom kroku animácie,
     *                        typicky na prekreslenie herného plátna.
     */
    public EmptyHandGraphics(Runnable repaintCallback) {
        this.punchTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EmptyHandGraphics.this.punchStep < 5) {
                    EmptyHandGraphics.this.punchOffset = EmptyHandGraphics.this.punchStep * 5;
                } else if (EmptyHandGraphics.this.punchStep < 10) {
                    EmptyHandGraphics.this.punchOffset = (10 - EmptyHandGraphics.this.punchStep) * 5;
                } else {
                    EmptyHandGraphics.this.punchOffset = 0;
                    EmptyHandGraphics.this.punchTimer.stop();
                    EmptyHandGraphics.this.punching = false;
                }
                EmptyHandGraphics.this.punchStep++;
                repaintCallback.run();
            }
        });
    }

    /**
     * Vykreslí ruky hráča s animáciou úderu.
     * Ruky sú vykreslené ako ovály a ich vertikálna pozícia sa mení podľa animácie.
     *
     * @param g2d Grafický kontext pre vykreslenie
     * @param player Inštancia hráča, podľa ktorej sa určuje pozícia rúk
     * @param armOffset Posunutie paží (môže slúžiť na ďalšie animácie alebo efekty)
     */
    @Override
    public void drawGraphics(Graphics2D g2d, Player player, int armOffset) {
        int leftArmY = player.getY() - 10 - armOffset - this.punchOffset;
        int rightArmY = player.getY() - 10 - armOffset - this.punchOffset;

        int leftHandX = player.getX() - 5 ;
        int rightHandX = player.getX() + 35 ;

        g2d.setColor(new Color(184, 135, 97));
        g2d.fillOval(leftHandX, leftArmY, 20, 20);
        g2d.fillOval(rightHandX, rightArmY, 20, 20);
    }

    /**
     * Spustí animáciu úderu, ak ešte neprebieha.
     */
    @Override
    public void use() {
        if (!this.punching) {
            this.punching = true;
            this.punchStep = 0;
            this.punchTimer.start();
        }
    }
}

