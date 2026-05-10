package effects;

import entity.Player;

public class Strength extends Effects {
    public Strength(long duration) {
        super(duration , "Strength");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.getActivate()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setDamage(10);
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDamage(3);
    }
}
