package graphics.infoGraphics;

import effects.Effects;
import managers.EffectsManager;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

/**
 * Trieda zodpovedná za vykresľovanie aktívnych efektov hráča.
 * Zobrazuje názvy efektov a zostávajúci čas ich pôsobenia.
 */
public class ActiveEffectsGraphics {
    /**
     * Vykreslí všetky aktívne efekty na obrazovku s ich zostávajúcim časom.
     *
     * @param g            grafický 2D kontext
     * @param effectsManager manažér efektov, z ktorého sa získajú aktívne efekty
     */
    public void drawActiveEffects(Graphics2D g, EffectsManager effectsManager) {
        ArrayList<Effects> activeEffects = effectsManager.getActiveEffects();
        int x = 10;
        int y = 60;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));

        for (Effects effect : activeEffects) {
            double remainingSeconds = effect.getRemainingTime() / 1000.0;
            g.drawString(effect.getName() + " " + String.format("%.1f", remainingSeconds) + "s", x, y);
            y += 20;
        }
    }

}
