package managers;

import effects.Effects;
import entities.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Manažér aktívnych efektov hry.
 *
 * Udržiava zoznam aktívnych efektov, poskytuje metódy na pridávanie náhodných
 * efektov, aktualizáciu stavu efektov každým tikom hry a vyčistenie zoznamu.
 * Táto trieda tiež inkapsuluje generátor náhodných čísel používaný pri výbere
 * nového efektu.
 */
public class EffectsManager {
    private final ArrayList<Effects> activeEffects;
    private final Random generator;

    /**
     * Vytvorí nový EffectsManager s prázdnym zoznamom efektov a vlastným Random generátorom.
     */
    public EffectsManager() {
        this.generator = new Random();
        this.activeEffects = new ArrayList<>();
    }

    /**
     * Vygeneruje náhodný efekt a pridá ho do zoznamu aktívnych efektov.
     *
     * Výber efektu je náhodný z pevného zoznamu dostupných efektov. Efekt bude
     * inicializovaný s pevne danou dĺžkou (v milisekundách) a pridaný do zoznamu
     * {@code activeEffects} pre následnú aktualizáciu a spracovanie.
     */
    public void addRandomEffect() {
        int index = this.generator.nextInt(7);

        Effects newEffect = switch (index) {
            case 0 -> new effects.Haste(5000);
            case 1 -> new effects.Slowness(5000);
            case 2 -> new effects.Strength(5000);
            case 3 -> new effects.Weakness(5000);
            case 4 -> new effects.Fatigue(5000);
            case 5 -> new effects.Efficiency(5000);
            case 6 -> new effects.DoubleGold(5000);
            default -> null;
        };

        this.activeEffects.add(newEffect);
    }

    /**
     * Aktualizuje všetky aktívne efekty pre daného hráča.
     *
     * Pre každý efekt sa skontroluje, či exspiroval. Ak áno, jeho účinok sa
     * zruší volaním {@code removeEffect(player)} a efekt sa z zoznamu odstráni.
     * Ak nie je exspirovaný, vykoná sa jeho pravidelná logika cez {@code useEffect(player)}.
     *
     * @param player cieľový hráč, na ktorom sa efekty aplikujú alebo odstraňujú
     */
    public void update(Player player) {
        Iterator<Effects> iterator = this.activeEffects.iterator();
        while (iterator.hasNext()) {
            Effects effect = iterator.next();
            if (effect.isExpired()) {
                effect.removeEffect(player);
                iterator.remove();
            } else {
                effect.useEffect(player);
            }
        }
    }

    /**
     * Odstráni všetky aktuálne aktívne efekty bez volania ich individuálneho
     * {@code removeEffect}. Používa sa napríklad pri resetovaní alebo ukončení hry.
     */
    public void clearEffects() {
        this.activeEffects.clear();
    }

    /**
     * Vráti referenciu na zoznam aktívnych efektov.
     *
     * Upozornenie: vracia priamu referenciu na interný zoznam, preto voliteľné
     * operácie mimo tejto triedy môžu meniť obsah. Používajte opatrne (napr. iba
     * na čítanie alebo na vykreslenie stavebného zoznamu efektov).
     *
     * @return modifikovateľný {@code ArrayList} aktívnych efektov
     */
    public ArrayList<Effects> getActiveEffects() {
        return this.activeEffects;
    }
}
