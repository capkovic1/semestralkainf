package weapon;

/**
 * Trieda predstavujúca zbraň typu Luk (Bow).
 * Rozširuje základnú triedu {@link Weapon} a implementuje špecifické správanie luku,
 * vrátane možnosti vylepšenia, ktoré zvyšuje poškodenie.
 */
public class Bow extends Weapon {

    /**
     * Konštruktor vytvárajúci nový luk so zadaným dosahom a poškodením.
     *
     * @param range  Dosah luku.
     * @param damage Počiatočné poškodenie luku.
     */
    public Bow(int range, int damage) {
        super(range, damage);
    }

    /**
     * Pokúsi sa vylepšiť luk.
     * Ak je vylepšenie úspešné (podľa základnej metódy upgrade),
     * zvýši poškodenie luku o 5.
     *
     * @return {@code true}, ak bolo vylepšenie úspešné, inak {@code false}.
     */
    public boolean upgrade() {
        boolean upgrade = super.upgrade();
        if (upgrade) {
            super.zvysDamage(5);
            return true;
        }
        return false;
    }
}
