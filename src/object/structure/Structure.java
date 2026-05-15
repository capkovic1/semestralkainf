package object.structure;

import entities.enemies.Enemy;

import java.awt.*;
import java.util.ArrayList;

public interface Structure {
    void update(ArrayList<Enemy> enemies);
    boolean isDestroyed();
    int getPrice();
    int getX();

    int getY();
    void draw(Graphics g);
    Rectangle getBounds();
}
