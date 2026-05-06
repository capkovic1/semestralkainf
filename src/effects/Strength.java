package effects;

import entity.Player;

public class Strength implements Effect {
    @Override
    public void useEffect(Player player) {
        player.setDamage(10);
    }
}
