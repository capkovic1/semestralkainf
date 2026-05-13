package main;

import entities.player.Player;
import graphics.gameGraphics.GameGraphics;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        Player player = new Player(5, 5,  5);

        GameGraphics gameGraphics = new GameGraphics(player);

        JFrame frame = new JFrame("2D Hra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(screenSize.width, screenSize.height);
        frame.setResizable(false);
        frame.add(gameGraphics);
        frame.setVisible(true);

        gameGraphics.requestFocusInWindow();
    }
}