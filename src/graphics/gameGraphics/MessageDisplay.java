package graphics.gameGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


/**
 * Táto trieda bola vygenerovaná AI
 *
 * Trieda zodpovedná za zobrazenie dočasných správ na obrazovke.
 * Správy sa zobrazujú na strede obrazovky s polopriehľadným pozadím.
 */
public class MessageDisplay {
    private String message = "";
    private long messageDisplayTime = 0;
    private static final long MESSAGEDURATION = 2000;
    
    /**
     * Vykreslí správu na obrazovku, ak je čas jej zobrazenia ešte aktívny.
     *
     * @param g grafický kontext na kreslenie
     * @param width šírka obrazovky
     * @param height výška obrazovky
     */
    public void draw(Graphics g , int width, int height) {
        if (System.currentTimeMillis() < this.messageDisplayTime) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g.getFontMetrics();
            int x = (width - fm.stringWidth(this.message)) / 2;
            int y = height / 2;

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(x - 10, y - fm.getHeight(), fm.stringWidth(this.message) + 20, fm.getHeight() + 10);

            g.setColor(Color.WHITE);
            g.drawString(this.message, x, y);
        }
        
    }
    
    /**
     * Nastaví správu na zobrazenie s predvoleným trvaním (2 sekundy).
     *
     * @param message text správy na zobrazenie
     */
    public void showMessage(String message) {
        this.message = message;
        this.messageDisplayTime = System.currentTimeMillis() + MESSAGEDURATION;
    }
}