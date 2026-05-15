package object.material;

import graphics.objectGraphics.materialGraphics.TreeGraphics;
import resource.ResourceType;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Trieda reprezentuje materiál typu drevo s určitým množstvom životov (hp).
 * Implementuje rozhranie {@link Material}, ktoré umožňuje meniť a zisťovať stav poškodenia.
 */
public class Tree implements Material {
    private int hp;
    private final int x;
    private final int y;

    /**
     * Vytvorí nový materiál drevo s počiatočným počtom životov.
     *
     * @param hp počiatočné množstvo životov dreva
     */
    public Tree(int hp , int x, int y) {
        this.hp = hp;
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    /**
     * Zmení hodnotu životov o zadané číslo (môže byť kladné alebo záporné).
     *
     * @param hp hodnota o ktorú sa má zmeniť množstvo životov
     */
    @Override
    public ResourceType changeHpBy(int hp) {
        this.hp += hp;
        return ResourceType.WOOD;
    }

    @Override
    public void draw(Graphics g) {
        new TreeGraphics(this).draw(g);
    }

    public Rectangle getBounds() {
        int size = 40;
        return new Rectangle(this.x - size, this.y - size, size * 2, size * 2);
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
