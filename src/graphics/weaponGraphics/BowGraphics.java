package graphics.weaponGraphics;

import entities.player.Player;
import managers.ProjectileManager;
import projectile.Arrow;


import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Path2D;

import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Táto trieda bola vygenerovaná AI
 *
 * Trieda BowGraphics zodpovedá za grafické vykreslenie luku s možnosťou
 * natiahnutia a vystrelenia šípu. Implementuje rozhranie HandGraphics.
 * Používa vnútorný timer na animáciu natiahnutia luku a zobrazenie šípu.
 */
public class BowGraphics implements HandGraphics {
    /**
     * Stav natiahnutia luku: 0 = voľný, 1 = natiahnutie, 2 = uvoľnený so šípom.
     */
    private int drawState = 0;

    /**
     * Timer na animáciu natiahnutia a uvoľnenia luku.
     */
    private final Timer drawTimer;

    /**
     * Aktuálny progres natiahnutia luku v percentách (0-100).
     */
    private int drawProgress = 0;

    private Player player;

    /**
     * Konštruktor triedy BowGraphics.
     */
    public BowGraphics() {


        this.drawTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (BowGraphics.this.drawState == 1) {
                    if (BowGraphics.this.drawProgress < 100) {
                        BowGraphics.this.drawProgress += 5;
                    } else {
                        BowGraphics.this.drawState = 2;
                    }
                } else if (BowGraphics.this.drawState == 2) {
                    if (BowGraphics.this.drawProgress > 0) {
                        BowGraphics.this.drawProgress -= 10;
                    } else {
                        BowGraphics.this.drawState = 0;
                        BowGraphics.this.drawTimer.stop();
                    }
                }
                repaintAllFrames();
            }
        });
    }

    /**
     * Vykreslí luk a prípadne šíp podľa aktuálneho stavu natiahnutia.
     * Luk je vykreslený v pozícii hráča a rotovaný podľa jeho uhla.
     *
     * @param g2d Grafický kontext na vykreslenie
     * @param player Inštancia hráča, podľa ktorej sa určuje pozícia a uhol luku
     * @param armOffset Nepoužívaný parameter na prípadné posunutie paže (môže byť rozšírený)
     */
    @Override
    public void draw(Graphics2D g2d, Player player, int armOffset) {
        this.player = player;

        int playerCenterX = player.getX() + 25;
        int playerCenterY = player.getY() + 25;
        int bowOffsetX = 40;
        int bowX = playerCenterX + bowOffsetX;
        int bowOffsetY = -10;
        int bowY = playerCenterY + bowOffsetY;

        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(playerCenterX, playerCenterY);
        g2d.rotate(Math.toRadians(player.getAngle()));
        g2d.translate(-playerCenterX, -playerCenterY);

        int bowLength = 70;
        int bowWidth = 25;
        int stringOffset = (int)(bowWidth * 0.7 * (this.drawProgress / 100.0));

        g2d.setColor(new Color(139, 69, 19));
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        QuadCurve2D bowCurve = new QuadCurve2D.Float(
                bowX - (float)bowLength / 2, bowY,
                bowX, bowY - bowWidth + stringOffset,
                bowX + (float)bowLength / 2, bowY
        );
        g2d.draw(bowCurve);

        g2d.setStroke(new BasicStroke(6));
        g2d.drawLine(bowX - 8, bowY, bowX + 8, bowY);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        if (this.drawProgress > 0) {

            Path2D stringPath = new Path2D.Float();
            stringPath.moveTo(bowX - (double)bowLength / 2, bowY);

            double angleRad = Math.toRadians(player.getAngle());
            int pullX = (int)(playerCenterX + 15 * Math.cos(angleRad));
            int pullY = (int)(playerCenterY + 15 * Math.sin(angleRad));

            stringPath.quadTo(pullX, pullY, bowX + (double)bowLength / 2, bowY);
            g2d.draw(stringPath);

        } else {
            g2d.drawLine(bowX - bowLength / 2, bowY, bowX + bowLength / 2, bowY);
        }

        g2d.setColor(new Color(238, 188, 156));
        g2d.fillOval(bowX - 10, bowY - 10, 20, 20);

        g2d.setTransform(oldTransform);
    }

    /**
     * Aktivuje luk, začína natiahnutie šípu alebo znovu natiahne ak je už natiahnutý.
     * Spúšťa alebo pokračuje animáciu natiahnutia.
     */
    @Override
    public void use() {
        if (this.drawState == 0) {
            this.drawState = 1;
            this.drawTimer.start();
        } else if (this.drawState == 2) {
            this.drawState = 1;
        }

        double angleRad = Math.toRadians(this.player.getAngle());
        double speed = 10;
        double dx = Math.cos(angleRad) * speed;
        double dy = Math.sin(angleRad) * speed;

        int playerCenterX = this.player.getX() + 25;
        int playerCenterY = this.player.getY() + 25;
        int spawnDistance = 32;
        int x = playerCenterX + (int)(spawnDistance * Math.cos(angleRad));
        int y = playerCenterY + (int)(spawnDistance * Math.sin(angleRad));

        ProjectileManager.getInstance().addProjectile(new Arrow(x, y, (int)speed, this.player.getWeapon().getDamage(), dx, dy));
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
