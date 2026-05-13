package effects;

import entities.player.Player;

public class Fatigue extends Effects {
    public Fatigue(long duration) {
        super(duration , "Fatigue");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setSpeed(3);
            player.setEfficiency(1);
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultEfficiency();
        player.setDefaultSpeed();
    }
}
