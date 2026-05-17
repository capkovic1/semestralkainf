package managers;

import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Trieda GameHistory poskytuje funkcie na ukladanie a zobrazovanie histórie hier.
 * História sa ukladá do textového súboru a obsahuje dátum a čas,
 * dĺžku prežitia v hre a dosiahnuté skóre.
 *
 * tato trieda bola upravena AI
 */
public abstract class GameHistory {
    private static final String FILE_PATH = "src/History.txt";
    /**
     * Ukladá údaje o hre do histórie do textového súboru.
     * Zaznamenáva dátum a čas uloženia, čas prežitia hráča a jeho skóre.
     *
     * @param score skóre dosiahnuté hráčom
     * @param startTime čas začiatku hry v milisekundách (System.currentTimeMillis)
     */
    public static void saveHistory(int score, long startTime) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH, true);
            BufferedWriter bw = new BufferedWriter(fw);

            long currentTime = System.currentTimeMillis();
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTime));

            long timeAliveMillis = currentTime - startTime;
            int seconds = (int)(timeAliveMillis / 1000) % 60;
            int minutes = (int)(timeAliveMillis / (1000 * 60));

            String timeAliveFormatted = String.format("%02d:%02d", minutes, seconds);

            bw.write("Time: " + dateTime + " ,TimeAlive: " + timeAliveFormatted + ", Score: " + score);
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Zobrazí uloženú históriu hier v okne so scrollovacím textovým poľom.
     * Ak súbor s históriou neexistuje, zobrazí informačné hlásenie.
     *
     * @param parentComponent rodičovská komponenta, ku ktorej sa dialóg vzťahuje
     */
    public static void showHistory(Component parentComponent) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(parentComponent, "No history found.", "History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            Scanner scanner = new Scanner(file);
            StringBuilder history = new StringBuilder();
            while (scanner.hasNextLine()) {
                history.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            JTextArea textArea = new JTextArea(history.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(parentComponent, scrollPane, "Game History", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Error reading history file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}