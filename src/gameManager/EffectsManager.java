package gameManager;

import effects.Effects;
import entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EffectsManager {
    private ArrayList<Effects> activeEffects;
    private Random generator;

    public EffectsManager() {
        this.generator = new Random();
        this.activeEffects = new ArrayList<>();
    }

    public void addRandomEffect() {
        int index = this.generator.nextInt(7);

        Effects newEffect = null;
        switch(index) {
            case 0: newEffect = new effects.Haste(5000); break;
            case 1: newEffect = new effects.Slowness(5000); break;
            case 2: newEffect = new effects.Strength(5000); break;
            case 3: newEffect = new effects.Weakness(5000); break;
            case 4: newEffect = new effects.Fatigue(5000); break;
            case 5: newEffect = new effects.Efficiency(5000); break;
            case 6: newEffect = new effects.DoubleGold(5000); break;
        }

        this.activeEffects.add(newEffect);
    }



    public void update(Player player) {
        Iterator<Effects> iterator = this.activeEffects.iterator();
        boolean expiredEffect = false;
        while (iterator.hasNext()) {
            Effects effect = iterator.next();
            if (effect.isExpired()) {
                effect.removeEffect(player);
                iterator.remove();
            } else {
                effect.useEffect(player);
            }
        }
    }
    public void clearEffects() {
        this.activeEffects.clear();
    }
    public ArrayList<Effects> getActiveEffects() {
        return this.activeEffects;
    }
}
