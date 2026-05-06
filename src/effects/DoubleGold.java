package effects;

import entity.Player;
import resource.ResourceType;

public class DoubleGold implements Effect {

    @Override
    public void useEffect(Player player) {
        player.addResource(ResourceType.GOLD , player.getGold());
    }
}
