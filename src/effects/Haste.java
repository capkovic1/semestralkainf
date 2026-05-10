package effects;

import entity.Player;

public class Haste extends Effects {
    public Haste(long duration) {
        super(duration , "Haste");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.getActivate()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setSpeed(7);
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setSpeed(5);
    }
}
