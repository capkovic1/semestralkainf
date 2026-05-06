package effects;

import entity.Player;

public class Slowness implements Effect {
    @Override
    public void useEffect(Player player) {
        player.setSpeed(3);
    }
}
