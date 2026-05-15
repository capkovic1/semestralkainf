package effects;

import entities.player.Player;

/**
 * Efekt, ktorý zvýši efektivitu hráča (napr. zmení hodnotu efficiency) na dobu trvania.
 */
public class Efficiency extends Effects {
    /**
     * Vytvorí nový efekt Efficiency so zadaným trvaním.
     *
     * @param duration trvanie efektu v milisekundách
     */
    public Efficiency(long duration) {
        super(duration , "Efficiency");
    }

    /**
     * Aktivuje efekt pre hráča - nastaví vyššiu efektivitu.
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    @Override
    public void useEffect(Player player) {
        if (!this.isActive()) {
            this.setActivateState(true);
            super.setStartTime(System.currentTimeMillis());
            player.setEfficiency(5);
        }
    }

    /**
     * Odstráni efekt - obnoví predvolenú efektivitu hráča.
     *
     * @param player hráč, z ktorého sa efekt odstraňuje
     */
    @Override
    public void removeEffect(Player player) {
        super.setActivateState(false);
        player.setDefaultEfficiency();
    }
}
