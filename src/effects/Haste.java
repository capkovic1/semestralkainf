package effects;

import entity.Player;

public class Haste implements Effect {
    @Override
    public void useEffect(Player player) {
        player.setSpeed(7);
    }
}
