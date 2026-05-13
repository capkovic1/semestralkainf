package effects;

import entities.player.Player;

public class Slowness extends Effects {
    public Slowness(long duration) {
        super(duration , "Slowness");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            if (!this.isActive()) {
                this.setActivateState(true);
                super.setStartTime(System.currentTimeMillis());
                player.setSpeed(3);
            }
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultSpeed();
    }
}
