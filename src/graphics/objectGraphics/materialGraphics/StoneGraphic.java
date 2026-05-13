package graphics.objectGraphics.materialGraphics;

import object.material.Material;
import object.material.Stone;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Rectangle;

import java.awt.geom.GeneralPath;
import java.util.Random;

/**
 * Trieda StoneGraphic zabezpečuje vykresľovanie kamienka (stone)
 * so "náhodným" nepravidelným tvarom pomocou GeneralPath.
 */
public class StoneGraphic implements MaterialGraphics {


    /**
     * Základná veľkosť kamienka
     */
    private static final int SIZE = 40;

    /**
     * Tvar kamienka reprezentovaný GeneralPath
     */
    private final GeneralPath stoneShape;

    /**
     * Pozícia kamienka na obrazovke
     */
    private final int x;
    private final int y;

    /**
     * Konštruktor, ktorý nastaví pozíciu kamienka a vygeneruje jeho tvar.
     *
     */
    public StoneGraphic(Stone stone) {
        this.x = stone.getX();
        this.y = stone.getY();
        this.stoneShape = new GeneralPath();
        this.generateStoneShape();
    }

    /**
     * Vykreslí kamienok na grafický kontext.
     * Kamienok je vyplnený sivou farbou, s čiernym obrysom.
     *
     * @param g grafický kontext
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Nastavenie farby na sivú a vyplnenie tvaru
        g2d.setColor(new Color(169, 169, 169));
        g2d.fill(this.stoneShape);

        // Nastavenie farby na čiernu a nakreslenie obrysu s hrúbkou 2
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(this.stoneShape);

        g2d.dispose();
    }

    /**
     * Generuje nepravidelný tvar kamienka pomocou GeneralPath.
     * Používa pseudo-náhodný generátor s pevnou seed hodnotou založenou na
     * súradniciach, aby tvar bol konzistentný pre rovnakú pozíciu.
     */
    private void generateStoneShape() {
        Random rand = new Random(this.x * 1000L + this.y * 500L);

        int numPoints = 12;                    // Počet vrcholov polygonu
        double angleIncrement = 2 * Math.PI / numPoints;  // Uhol medzi vrcholmi

        // Začiatočný bod tvaru - náhodne posunutý v rámci SIZE
        this.stoneShape.moveTo(this.x + rand.nextInt(SIZE), this.y + rand.nextInt(SIZE));

        // Generovanie vrcholov polygonu v kruhu s náhodnou odchýlkou polomeru
        for (int i = 1; i < numPoints; i++) {
            double angle = i * angleIncrement;
            double radius = SIZE + rand.nextInt(SIZE / 2);

            // Vypočítanie súradníc s pridanou náhodnou odchýlkou ±5 pixelov
            int newX = (int) (this.x + radius * Math.cos(angle) + rand.nextInt(10) - 5);
            int newY = (int) (this.y + radius * Math.sin(angle) + rand.nextInt(10) - 5);

            this.stoneShape.lineTo(newX, newY);
        }

        this.stoneShape.closePath();  // Uzavrie polygon
    }
}

