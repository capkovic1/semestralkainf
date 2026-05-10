package effects;

import entity.Player;

public class Efficiency extends Effects {
    public Efficiency(long duration) {
        super(duration , "Efficiency");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.getActivate()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setEfficiency(5);
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setEfficiency(3);
    }
}
