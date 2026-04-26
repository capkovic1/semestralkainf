package weapon;

/**
 * Trieda predstavujúca zbraň typu Kladivo (Hammer).
 * Rozširuje základnú triedu {@link Weapon} a poskytuje špecifické vylepšenia,
 * ktoré zvyšujú poškodenie na štruktúrach aj všeobecné poškodenie.
 */
public class Hammer extends Weapon {

    /**
     * Konštruktor vytvárajúci nové kladivo so zadaným dosahom a poškodením.
     *
     * @param range  Dosah kladiva.
     * @param damage Počiatočné poškodenie kladiva.
     */
    public Hammer(int range, int damage) {
        super(range, damage);
    }

    /**
     * Pokúsi sa vylepšiť kladivo.
     * Ak je vylepšenie úspešné (podľa základnej metódy upgrade),
     * zvýši poškodenie na štruktúrach a všeobecné poškodenie o 5.
     *
     * @return {@code true}, ak bolo vylepšenie úspešné, inak {@code false}.
     */
    public boolean upgrade() {
        boolean upgrade = super.upgrade();
        if (upgrade) {
            super.zvysDmgToStructures(5);
            super.increaseDamage(5);
            return true;
        }
        return false;
    }
}

