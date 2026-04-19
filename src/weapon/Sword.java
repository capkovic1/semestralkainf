package weapon;

/**
 * Trieda predstavujúca zbraň typu Meč (Sword).
 * Rozširuje základnú triedu {@link Weapon} a umožňuje vylepšenie
 * zvýšením poškodenia o väčšiu hodnotu.
 */
public class Sword extends Weapon {

    /**
     * Konštruktor vytvárajúci nový meč so zadaným dosahom a poškodením.
     *
     * @param range  Dosah meča.
     * @param damage Počiatočné poškodenie meča.
     */
    public Sword(int range, int damage) {
        super(range, damage);
    }

    /**
     * Pokúsi sa vylepšiť meč.
     * Ak je vylepšenie úspešné (podľa základnej metódy upgrade),
     * zvýši poškodenie o 10.
     *
     * @return {@code true}, ak bolo vylepšenie úspešné, inak {@code false}.
     */
    public boolean upgrade() {
        boolean upgrade = super.upgrade();
        if (upgrade) {
            super.zvysDamage(10);
        }
        return upgrade;
    }
}

