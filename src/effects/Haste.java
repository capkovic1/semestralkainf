package effects;

import entities.player.Player;

/**
 * Efekt Haste zvyšuje rýchlosť hráča na určitý čas.
 */
public class Haste extends Effects {
    /**
     * Vytvorí efekt Haste so zadaným trvaním.
     *
     * @param duration trvanie efektu v milisekundách
     */
    public Haste(long duration) {
        super(duration , "Haste");
    }

    /**
     * Aktivuje efekt - zvýši rýchlosť hráča.
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setSpeed(7);
        }
    }

    /**
     * Odstráni efekt - obnoví predvolené hodnoty.
     *
     * @param player hráč, z ktorého sa efekt odstraňuje
     */
    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultEfficiency();
    }
}
