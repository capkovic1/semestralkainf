package effects;

import entity.Player;

public class Slowness extends Effects {
    public Slowness(long duration) {
        super(duration , "Slowness");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.getActivate()) {
            this.setActivateState(true);
            if (!this.getActivate()) {
                this.setActivateState(true);
                super.setStartTime(System.currentTimeMillis());
                player.setSpeed(3);
            }
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);

        player.setSpeed(5);
    }
}
