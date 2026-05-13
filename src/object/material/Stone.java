package object.material;

import graphics.objectGraphics.materialGraphics.StoneGraphic;
import resource.ResourceType;

import java.awt.*;

/**
 * Trieda reprezentujúca kameň ako materiál s určitým množstvom životov (HP).
 * Implementuje rozhranie {@link Material}, takže môže byť poškodzovaný a
 * môže byť zničený, keď jeho HP dosiahne 0 alebo menej.
 */
public class Stone implements Material {
    private int hp;
    private int x;
    private int y;

    /**
     * Vytvorí nový kameň s daným počtom životov.
     *
     * @param hp počiatočné množstvo životov kamena
     */
    public Stone(int hp , int x, int y) {
        this.hp = hp;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
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

    @Override
    public Rectangle getBounds() {
        int SIZE = 40;
        return new Rectangle(this.x - SIZE, this.y - SIZE, SIZE * 2, SIZE * 2);
    }

    @Override
    public void draw(Graphics g) {
        new StoneGraphic(this).draw(g);
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
