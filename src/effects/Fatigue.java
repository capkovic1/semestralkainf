package effects;

import entity.Player;

public class Fatigue extends Effects {
    public Fatigue(long duration) {
        super(duration , "Fatigue");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.getActivate()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setSpeed(3);
            player.setEfficiency(1);
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setSpeed(5);
        player.setEfficiency(3);
    }
}
