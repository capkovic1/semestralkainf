package effects;

import entities.player.Player;

public class Strength extends Effects {
    public Strength(long duration) {
        super(duration , "Strength");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setDamage(10);
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultStrength();
    }
}
