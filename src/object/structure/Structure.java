package object.structure;

import entity.Enemy;

import java.util.ArrayList;

public interface Structure {
    void update(ArrayList<Enemy> enemies);
    boolean isDestroyed();
    int getPrice();
    int getX();

    int getY();
}
