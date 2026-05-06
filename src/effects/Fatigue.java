package effects;

import entity.Player;

public class Fatigue implements Effect {
    @Override
    public void useEffect(Player player) {
        player.setSpeed(3);
        player.setEfficiency(1);
    }
}
