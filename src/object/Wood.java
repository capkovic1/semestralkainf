package object;

/**
 * Trieda reprezentuje materiál typu drevo s určitým množstvom životov (hp).
 * Implementuje rozhranie {@link Material}, ktoré umožňuje meniť a zisťovať stav poškodenia.
 */
public class Wood implements Material {
    private int hp;

    /**
     * Vytvorí nový materiál drevo s počiatočným počtom životov.
     *
     * @param hp počiatočné množstvo životov dreva
     */
    public Wood(int hp) {
        this.hp = hp;
    }

    /**
     * Zmení hodnotu životov o zadané číslo (môže byť kladné alebo záporné).
     *
     * @param hp hodnota o ktorú sa má zmeniť množstvo životov
     */
    @Override
    public void changeHpBy(int hp) {
        this.hp += hp;
    }

    /**
     * Skontroluje, či je materiál zničený (životy menej alebo rovné nule).
     *
     * @return {@code true} ak je materiál zničený, inak {@code false}
     */
    @Override
    public boolean isDestroyed() {
        return this.hp <= 0;
    }
}
