package graphics.gameGraphics;

import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * Trieda zodpovedná za vykresľovanie hracej plochy ako mriežky buniek.
 * Každá bunka je reprezentovaná prázdnou bunkou (EmptyCellGraphics).
 */
public class BoardGraphics extends JPanel {

    /** Počet riadkov v hracej ploche. */
    private final int rows;

    /** Počet stĺpcov v hracej ploche. */
    private final int cols;

    /** Inštancia grafiky pre prázdnu bunku. */
    private final EmptyCellGraphics emptyCellGraphics;

    /**
     * Vytvorí novú hraciu plochu so zadaným počtom riadkov a stĺpcov.
     *
     * @param rows Počet riadkov na hracej ploche.
     * @param cols Počet stĺpcov na hracej ploche.
     */
    public BoardGraphics(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.emptyCellGraphics = new EmptyCellGraphics();
    }

    /**
     * Vykreslí všetky bunky na hracej ploche.
     * Metóda sa volá automaticky Swingom pri potrebe prekreslenia komponentu.
     *
     * @param g Grafický kontext, do ktorého sa vykresľuje.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.emptyCellGraphics.getEmptyCellGraphics(g, j, i);
            }
        }
    }
}
