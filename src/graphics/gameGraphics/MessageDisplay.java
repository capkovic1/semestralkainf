package graphics.gameGraphics;

import java.awt.*;

public class MessageDisplay {
    private String message = "";
    private long messageDisplayTime = 0;
    private static final long MESSAGEDURATION = 2000;
    
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
    
    public void showMessage(String message) {
        this.message = message;
        this.messageDisplayTime = System.currentTimeMillis() + MESSAGEDURATION;
    }
}