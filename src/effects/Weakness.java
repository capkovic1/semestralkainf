package effects;

import entity.Player;

public class Weakness extends Effects {

    public Weakness(long duration) {
        super(duration , "Weakness");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.getActivate()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setDamage(1);
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDamage(3);
    }
}
