package effects;

import entities.player.Player;

/**
 * Efekt Strength zvyšuje poškodenie hráča po dobu trvania efektu.
 */
public class Strength extends Effects {
    /**
     * Vytvorí nový efekt Strength so zadaným trvaním.
     *
     * @param duration trvanie efektu v milisekundách
     */
    public Strength(long duration) {
        super(duration , "Strength");
    }

    /**
     * Aktivuje efekt - zvýši damage hráča.
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setDamage(10);
        }
    }

    /**
     * Odstráni efekt a obnoví predvolenú silu hráča.
     *
     * @param player hráč, z ktorého sa efekt odstraňuje
     */
    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultStrength();
    }
}
