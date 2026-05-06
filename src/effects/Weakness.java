package effects;

import entity.Player;

public class Weakness implements Effect {
    @Override
    public void useEffect(Player player) {
        player.setDamage(1);
    }
}
