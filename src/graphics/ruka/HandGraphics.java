package graphics.ruka;

import entity.Player;

import java.awt.Graphics2D;

/**
 * Rozhranie reprezentujúce grafiku ruky alebo predmetu drženého v ruke hráča.
 * Poskytuje metódy na vykreslenie grafiky a aktiváciu použitia predmetu/ruky.
 */
public interface HandGraphics {

    /**
     * Vykreslí grafiku ruky alebo predmetu podľa aktuálneho stavu hráča a posunu ramena.
     *
     * @param g2d grafický kontext pre kreslenie
     * @param player inštancia hráča, na základe ktorej sa určuje pozícia a orientácia
     * @param armOffset posunutie ramena, ktoré môže slúžiť na animácie alebo efekty
     */
    void drawGraphics(Graphics2D g2d, Player player, int armOffset);

    /**
     * Aktivuje použitie ruky alebo predmetu (napríklad útok, animáciu atď.).
     */
    void use();
}
