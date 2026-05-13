package graphics.infoGraphics;

import entities.player.Player;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Trieda zodpovedná za vykresľovanie informácií o hráčovi,
 * ako sú jeho zdroje, herný čas a ukazovateľ zdravia.
 */
public class PlayerInfoGraphics {
    private final Player player;

    /**
     * Konštruktor inicializuje PlayerInfoGraphics so zadaným hráčom,
     * ktorého údaje sa budú zobrazovať.
     *
     * @param player Inštancia hráča, ktorého informácie sa majú zobrazovať.
     */
    public PlayerInfoGraphics(Player player) {
        this.player = player;
    }

    /**
     * Vykreslí hráčove zdroje (drevo, kameň, zlato), čas hry a zdravotný ukazovateľ.
     *
     * @param g Grafický kontext na vykreslenie informácií.
     * @param width Šírka plátna pre správne zarovnanie textu.
     * @param height Výška plátna pre správne zarovnanie textu.
     * @param startTime Čas začiatku hry v milisekundách, slúži na výpočet uplynutého času.
     */
    public void drawPlayerResources(Graphics g, int width, int height , long startTime) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        int wood = this.player.getWood();
        int stone = this.player.getStone();
        int gold = this.player.getGold();

        int x = width - 150;
        int y = height - 50;

        g2d.drawString("Wood: " + wood, x, y);
        g2d.drawString("Stone: " + stone, x, y + 25);
        g2d.drawString("Gold: " + gold, x, y + 50);

        this.drawGameTime(g, width, startTime);
        this.drawHealthBar(g);
    }

    /**
     * Vykreslí aktuálny herný čas od štartu v tvare MM:SS,
     * zarovnaný vpravo hore obrazovky.
     *
     * @param g Grafický kontext na vykreslenie času.
     * @param width Šírka plátna pre správne zarovnanie textu.
     * @param startTime Čas začiatku hry v milisekundách.
     */
    private void drawGameTime(Graphics g, int width, long startTime) {
        long currentTime = System.currentTimeMillis();
        long elapsedMillis = currentTime - startTime;

        int seconds = (int)(elapsedMillis / 1000) % 60;
        int minutes = (int)(elapsedMillis / (1000 * 60));

        String timeString = String.format("Time: %02d:%02d", minutes, seconds);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);

        int margin = 20;
        FontMetrics fm = g2d.getFontMetrics();
        int x = width - fm.stringWidth(timeString) - margin;
        int y = fm.getAscent() + margin;

        g2d.drawString(timeString, x, y);
    }

    /**
     * Vykreslí zdravotný ukazovateľ hráča ako farebný pruh,
     * kde červená značí maximálne zdravie a zelená aktuálny stav.
     * Zdravotný stav je zobrazený aj textovo nad pruhom.
     *
     * @param g Grafický kontext na vykreslenie zdravotného pruhu.
     */
    private void drawHealthBar(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        int healthWidth = 200;
        int healthHeight = 20;
        int healthX = 20;
        int healthY = 20;

        float healthPercent = Math.max(0, Math.min(1,
                (float)this.player.getHealth() / this.player.getMaxHealth()));

        g2d.setColor(Color.RED);
        g2d.fillRect(healthX, healthY, healthWidth, healthHeight);

        g2d.setColor(Color.GREEN);
        g2d.fillRect(healthX, healthY, (int)(healthWidth * healthPercent), healthHeight);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(healthX, healthY, healthWidth, healthHeight);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        String healthText = "Health: " + this.player.getHealth() + "/" + this.player.getMaxHealth();

        FontMetrics fm = g2d.getFontMetrics();
        int textX = healthX + (healthWidth - fm.stringWidth(healthText)) / 2;
        int textY = healthY - 5;

        g2d.drawString(healthText, textX, textY);
    }
}
