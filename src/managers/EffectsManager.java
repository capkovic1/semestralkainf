package managers;

import effects.Effects;
import entities.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EffectsManager {
    private final ArrayList<Effects> activeEffects;
    private final Random generator;

    public EffectsManager() {
        this.generator = new Random();
        this.activeEffects = new ArrayList<>();
    }

    public void addRandomEffect() {
        int index = this.generator.nextInt(7);

        Effects newEffect = switch (index) {
            case 0 -> new effects.Haste(5000);
            case 1 -> new effects.Slowness(5000);
            case 2 -> new effects.Strength(5000);
            case 3 -> new effects.Weakness(5000);
            case 4 -> new effects.Fatigue(5000);
            case 5 -> new effects.Efficiency(5000);
            case 6 -> new effects.DoubleGold(5000);
            default -> null;
        };

        this.activeEffects.add(newEffect);
    }

    public void update(Player player) {
        Iterator<Effects> iterator = this.activeEffects.iterator();
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
