package effects;

import entity.Player;
import resource.ResourceType;

public class DoubleGold extends Effects {
    private int goldAmount;

    public DoubleGold(long duration) {
        super(duration , "Double Gold");
    }

    @Override
    public void useEffect(Player player) {
        if (!this.getActivate()){
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            this.goldAmount = player.getGold();
        }
    }

    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.addResource(ResourceType.GOLD, player.getGold() - this.goldAmount);
    }
}
