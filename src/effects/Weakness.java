package effects;

import entities.player.Player;

/**
 * Efekt Weakness znižuje poškodenie hráča na určitý čas.
 */
public class Weakness extends Effects {

    /**
     * Vytvorí efekt Weakness so zadaným trvaním.
     *
     * @param duration trvanie efektu v milisekundách
     */
    public Weakness(long duration) {
        super(duration , "Weakness");
    }

    /**
     * Aktivuje efekt - zníži damage hráča.
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setDamage(1);
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
