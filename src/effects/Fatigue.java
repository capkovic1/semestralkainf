package effects;

import entities.player.Player;

/**
 * Efekt Fatigue znižuje rýchlosť a efektivitu hráča na určitý čas.
 */
public class Fatigue extends Effects {
    /**
     * Vytvorí nový efekt Fatigue so zadaným trvaním.
     *
     * @param duration trvanie efektu v milisekundách
     */
    public Fatigue(long duration) {
        super(duration , "Fatigue");
    }

    /**
     * Aplikuje efekt na hráča (zníži speed a efficiency).
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setSpeed(3);
            player.setEfficiency(1);
        }
    }

    /**
     * Odstráni efekt a obnoví predvolené hodnoty hráča.
     *
     * @param player hráč, z ktorého sa efekt odstraňuje
     */
    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultEfficiency();
        player.setDefaultSpeed();
    }
}
