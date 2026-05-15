package effects;

import entities.player.Player;
import resource.ResourceType;

/**
 * Efekt, ktorý zdvojnásobuje získané zlato po dobu trvania efektu.
 * Pri aktivácii si uloží aktuálny stav zlata hráča a pri odstránení
 * pripočíta rozdiel späť (efekt implementovaný jednoduchým príkladom).
 */
public class DoubleGold extends Effects {
    private int goldAmount;

    /**
     * Vytvorí efekt DoubleGold s daným trvaním.
     *
     * @param duration trvanie efektu v milisekundách
     */
    public DoubleGold(long duration) {
        super(duration , "Double Gold");
    }

    /**
     * Aktivuje efekt pre daného hráča (uloží jeho aktuálne zlato).
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            this.goldAmount = player.getGold();
        }
    }

    /**
     * Odstráni efekt a upraví množstvo zlata hráča podľa rozdielu.
     *
     * @param player hráč, z ktorého sa efekt odstraňuje
     */
    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.addResource(ResourceType.GOLD, player.getGold() - this.goldAmount);
    }
}
