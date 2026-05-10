package graphics.infoGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * DamageIndicator zobrazuje damage text nad enemy-m a zmizne po čase.
 */
public class DamageIndicator {
    private int x;
    private int y;
    private int damage;
    private long createdTime;
    private static final long DISPLAY_DURATION = 1000; // 1 sekunda
    private static final float RISE_SPEED = 1.5f; // Pixely za update

    public DamageIndicator(int x, int y, int damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.createdTime = System.currentTimeMillis();
    }

    public void draw(Graphics2D g) {
        long elapsed = System.currentTimeMillis() - this.createdTime;

        if (elapsed > DISPLAY_DURATION) {
            return;
        }

        // Počítanie alpha (fade out efekt)
        float alpha = 1.0f - (float) elapsed / DISPLAY_DURATION;
        int alphaInt = (int) (alpha * 255);

        // Pohyb nahor
        float newY = this.y - (elapsed / 16.0f) * RISE_SPEED;

        // Vykresliť text
        g.setColor(new Color(255, 0, 0, alphaInt));
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("-" + this.damage,  this.x,  (int) newY);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.createdTime > DISPLAY_DURATION;
    }
}
