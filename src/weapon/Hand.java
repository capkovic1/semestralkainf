package weapon;

/**
 * Trieda predstavujúca zbraň typu Ruka (Hand).
 * Rozširuje základnú triedu {@link Weapon} a umožňuje jednoduché vylepšenie
 * zvýšením poškodenia.
 */
public class Hand extends Weapon {

    /**
     * Konštruktor vytvárajúci novú ruku so zadaným dosahom a poškodením.
     *
     * @param range  Dosah ruky.
     * @param damage Počiatočné poškodenie ruky.
     */
    public Hand(int range, int damage) {
        super(range, damage);
    }

    /**
     * Pokúsi sa vylepšiť ruku.
     * Ak je vylepšenie úspešné (podľa základnej metódy upgrade),
     * zvýši poškodenie o 3.
     *
     * @return {@code true}, ak bolo vylepšenie úspešné, inak {@code false}.
     */
    public boolean upgrade() {
        boolean upgrade = super.upgrade();
        if (upgrade) {
            super.increaseDamage(3);
            return true;
        }
        return false;
    }
}
