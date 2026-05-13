package weapon;

/**
 * Trieda predstavujúca základnú zbraň s úrovňou, dosahom, poškodením
 * a poškodením proti štruktúram.
 */
public abstract class Weapon {
    private static final int MAXLEVEL = 3;
    private int level;
    private int dmgToStructures;
    private final int range;
    private int damage;

    /**
     * Vytvorí novú zbraň s daným dosahom a poškodením.
     * Úroveň zbrane je nastavená na 1 a poškodenie proti štruktúram na 1.
     *
     * @param range  Dosah zbrane.
     * @param damage Počiatočné poškodenie zbrane.
     */
    public Weapon(int range, int damage) {
        this.level = 1;
        this.dmgToStructures = 1;
        this.range = range;
        this.damage = damage;
    }

    /**
     * Pokúsi sa zlepšiť zbraň zvýšením jej úrovne,
     * ak ešte nie je na maximálnej úrovni.
     *
     * @return {@code true}, ak bolo vylepšenie úspešné, inak {@code false}.
     */
    public boolean upgrade() {
        if (this.level < MAXLEVEL) {
            this.level++;
            return true;
        }
        return false;
    }

    /**
     * Zvýši poškodenie zbrane o zadanú hodnotu.
     * Chránená metóda, určená pre vnútorné použitie v triedach dedičných od {@code Zbran}.
     *
     * @param number Hodnota, o ktorú sa zvýši poškodenie.
     */
    protected void increaseDamage(int number) {
        this.damage += number;
    }

    /**
     * Zvýši poškodenie proti štruktúram o zadanú hodnotu.
     * Chránená metóda, určená pre vnútorné použitie v triedach dedičných od {@code Zbran}.
     *
     * @param number Hodnota, o ktorú sa zvýši poškodenie proti štruktúram.
     */
    protected void increaseDmgToStructures(int number) {
        this.dmgToStructures += number;
    }

    /**
     * Získa aktuálnu úroveň zbrane.
     *
     * @return Úroveň zbrane.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Získa dosah zbrane.
     *
     * @return Dosah zbrane.
     */
    public int getRange() {
        return this.range;
    }

    /**
     * Získa poškodenie zbrane.
     *
     * @return Hodnota poškodenia zbrane.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Získa poškodenie zbrane proti štruktúram.
     *
     * @return Hodnota poškodenia proti štruktúram.
     */
    public int getDmgToStructures() {
        return this.dmgToStructures;
    }
}
