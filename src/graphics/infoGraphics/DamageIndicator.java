package graphics.infoGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * DamageIndicator zobrazuje damage text nad enemy-m a zmizne po čase.
 *
 */
public class DamageIndicator {
    private final int x;
    private final int y;
    private final int damage;
    private final long createdTime;
    private static final long DISPLAY_DURATION = 1000; // 1 sekunda
    private static final float RISE_SPEED = 1.5f; // Pixely za update

    /**
     * Vytvorí nový DamageIndicator na zadanej pozícii s daným poškodením.
     *
     * @param x      X súradnica na obrazovke
     * @param y      Y súradnica na obrazovke
     * @param damage množstvo poškodenia na zobrazenie
     */
    public DamageIndicator(int x, int y, int damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.createdTime = System.currentTimeMillis();
    }

    /**
     * Táto metoda bola upravená AI
     *
     * Vykreslí damage indikátor s fade-out efektom a pohybom nahor.
     *
     * @param g grafický 2D kontext na kreslenie
     */
    public void draw(Graphics2D g) {
        long elapsed = System.currentTimeMillis() - this.createdTime;

        if (elapsed > DISPLAY_DURATION) {
            return;
        }

        // Počítanie alpha (fade out efekt)
        float alpha = 1.0f - (float)elapsed / DISPLAY_DURATION;
        int alphaInt = (int)(alpha * 255);

        // Pohyb nahor
        float newY = this.y - (elapsed / 16.0f) * RISE_SPEED;

        // Vykresliť text
        g.setColor(new Color(255, 0, 0, alphaInt));
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("-" + this.damage,  this.x,  (int)newY);
    }

    /**
     * Zisťuje, či je čas zobrazenia indikátora vypršaný.
     *
     * @return {@code true} ak je indikátor expired, inak {@code false}
     */
    public boolean isExpired() {
        return System.currentTimeMillis() - this.createdTime > DISPLAY_DURATION;
    }
}
