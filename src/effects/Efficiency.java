package effects;

import entity.Player;

public class Efficiency implements Effect {
    @Override
    public void useEffect(Player player) {
        player.setEfficiency(5);
    }
}
