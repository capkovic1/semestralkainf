package effects;

import entities.player.Player;

/**
 * Efekt Slowness znižuje rýchlosť hráča na určitý čas.
 */
public class Slowness extends Effects {
    /**
     * Vytvorí nový efekt Slowness so zadaným trvaním.
     *
     * @param duration trvanie efektu v milisekundách
     */
    public Slowness(long duration) {
        super(duration , "Slowness");
    }

    /**
     * Aktivuje efekt - nastaví zníženú rýchlosť hráča.
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            if (!this.isActive()) {
                this.setActivateState(true);
                super.setStartTime(System.currentTimeMillis());
                player.setSpeed(3);
            }
        }
    }

    /**
     * Odstráni efekt a obnoví predvolenú rýchlosť.
     *
     * @param player hráč, z ktorého sa efekt odstraňuje
     */
    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultSpeed();
    }
}
