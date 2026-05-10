package graphics.objectGraphics;

import object.material.Material;

import java.awt.*;

public interface MaterialGraphics {
    Material getMaterial();
    Rectangle getBounds();
    void draw(Graphics g);
}
