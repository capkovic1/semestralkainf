package object.material;

import resource.ResourceType;

/**
 * Trieda reprezentujúca kameň ako materiál s určitým množstvom životov (HP).
 * Implementuje rozhranie {@link Material}, takže môže byť poškodzovaný a
 * môže byť zničený, keď jeho HP dosiahne 0 alebo menej.
 */
public class Stone implements Material {
    private int hp;

    /**
     * Vytvorí nový kameň s daným počtom životov.
     *
     * @param hp počiatočné množstvo životov kamena
     */
    public Stone(int hp) {
        this.hp = hp;
    }

    /**
     * Zmení hodnotu životov kamena o zadané množstvo.
     * Hodnota môže byť kladná (liečenie) alebo záporná (poškodenie).
     *
     * @param hp množstvo, o ktoré sa zmenia životy kamena
     */
    @Override
    public ResourceType changeHpBy(int hp) {
        this.hp += hp;
        return ResourceType.STONE;
    }

    /**
     * Skontroluje, či je kameň zničený (keď má životy menšie alebo rovné nule).
     *
     * @return {@code true} ak je kameň zničený, inak {@code false}
     */
    @Override
    public boolean isDestroyed() {
        return this.hp <= 0;
    }
}
