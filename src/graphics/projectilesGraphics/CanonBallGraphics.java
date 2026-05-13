package graphics.projectilesGraphics;

import projectile.CanonBall;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;

/**
 * Trieda zodpovedná za vykresľovanie grafiky guľky kanónu.
 */
public class CanonBallGraphics implements ProjectileGraphics {
    private CanonBall canonBall;
    public CanonBallGraphics(CanonBall canonBall) {
        this.canonBall = canonBall;
    }
    /**
     * Vykreslí guľku kanónu na daný grafický kontext.
     *
     * @param g Grafický kontext, do ktorého sa guľka vykreslí.
     */
    public void draw(Graphics g) {
        if (this.canonBall == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D)g;
        Color oldColor = g2d.getColor();
        Stroke oldStroke = g2d.getStroke();

        try {
            g2d.setStroke(new BasicStroke(2));

            // Vypočítanie pozície s kontrolou hraníc
            int x = (int)Math.round(this.canonBall.getX());
            int y = (int)Math.round(this.canonBall.getY());

            if (x < -100 || y < -100 || x > 5000 || y > 5000) {
                return;
            }

            // Tmavá vonkajšia časť guľky
            g2d.setColor(new Color(100, 100, 100));
            g2d.fillOval(x - 5, y - 5, 10, 10);

            // Svetlejšia vnútorná časť guľky s priesvitnosťou
            g2d.setColor(new Color(255, 150, 50, 150));
            g2d.fillOval(x - 3, y - 3, 6, 6);

            // Obrys guľky
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - 5, y - 5, 10, 10);

        } finally {
            // Obnoví pôvodné nastavenia grafiky
            g2d.setColor(oldColor);
            g2d.setStroke(oldStroke);
        }
    }
}
