package graphics.infoGraphics;

import effects.Effects;
import gameManager.EffectsManager;

import java.awt.*;
import java.util.ArrayList;

public class ActiveEffectsGraphics {
    public void drawActiveEffects(Graphics2D g, EffectsManager effectsManager) {
        ArrayList<Effects> activeEffects = effectsManager.getActiveEffects();
        int x = 10;
        int y = 50;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));

        for (Effects effect : activeEffects) {
            g.drawString(effect.getName() + " " + effect.getRemainingTime(), x, y);
            y += 20;
        }
    }

}
