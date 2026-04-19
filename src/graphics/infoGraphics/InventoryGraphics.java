package graphics.infoGraphics;

import entity.Player;
import graphics.entityGraphics.PlayerGraphics;
import graphics.ruka.BowGraphics;
import graphics.ruka.EmptyHandGraphics;
import graphics.ruka.HandGraphics;
import graphics.ruka.HammerGraphics;
import graphics.ruka.SwordGraphics;
import weapon.Bow;
import weapon.Hammer;
import weapon.Hand;
import weapon.Sword;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Font;

/**
 * Trieda zodpovedná za vykresľovanie inventára hráča, výber zbraní
 * a ich grafickú reprezentáciu v hernom okne.
 * Inventár obsahuje pevne daný počet slotov so základnými zbraňami a
 * vizuálne zobrazuje vybraný slot zvýraznením.
 */
public class InventoryGraphics {
    private static final int SLOT_COUNT = 7;
    private static final int SLOT_SIZE = 50;
    private static final int SLOT_SPACING = 10;
    private static final int INVENTORY_HEIGHT = 80;
    private static final Color SLOT_COLOR = new Color(200, 200, 200, 150);
    private static final Color SELECTED_SLOT_COLOR = new Color(150, 150, 255, 200);

    private final Bow bow;
    private final Hammer hammer;
    private final Sword sword;
    private final Hand hand;

    private final BowGraphics bowGraphics;
    private final HammerGraphics hammerGraphics;
    private final SwordGraphics swordGraphics;
    private final HandGraphics handGraphics;


    private int selectedSlot = 0;
    private final Player player;
    private final PlayerGraphics playerGraphics;
    /**
     * Konštruktor vytvára inventár pre daného hráča a inicializuje zbrane
     * spolu s ich grafickými reprezentáciami.
     *
     * @param player Referencia na hráča, ktorému inventár patrí.
     * @param playerGraphics Referencia na grafiku hráča, umožňuje meniť
     *                       zobrazenie zbrane v rukách hráča.
     */
    public InventoryGraphics(Player player , PlayerGraphics playerGraphics) {
        this.player = player;
        this.playerGraphics = playerGraphics;

        this.bow = new Bow(0, 25);
        this.hammer = new Hammer(30, 20);
        this.sword = new Sword(60, 40);
        this.hand = new Hand(30, 10);

        this.bowGraphics = new BowGraphics(this.playerGraphics::repaint);
        this.hammerGraphics = new HammerGraphics(this.playerGraphics::repaint);
        this.swordGraphics = new SwordGraphics(this.playerGraphics::repaint);
        this.handGraphics = new EmptyHandGraphics(this.playerGraphics::repaint);
    }
    /**
     * Vykreslí inventár na spodnú časť obrazovky.
     * Zobrazí všetky sloty, ich čísla a názvy zbraní v každom slote.
     * Vybraný slot zvýrazní inou farbou.
     *
     * @param g Grafický kontext pre vykreslenie inventára.
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int inventoryWidth = (SLOT_COUNT * SLOT_SIZE) + ((SLOT_COUNT - 1) * SLOT_SPACING);
        int startX = (screenWidth - inventoryWidth) / 2;
        int startY = Toolkit.getDefaultToolkit().getScreenSize().height - INVENTORY_HEIGHT;

        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(0, startY - 10, screenWidth, INVENTORY_HEIGHT + 20);

        for (int i = 0; i < SLOT_COUNT; i++) {
            int x = startX + (i * (SLOT_SIZE + SLOT_SPACING));

            g2d.setColor(i == this.selectedSlot ? SELECTED_SLOT_COLOR : SLOT_COLOR);
            g2d.fillRoundRect(x, startY, SLOT_SIZE, SLOT_SIZE, 10, 10);

            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, SLOT_SIZE, SLOT_SIZE, 10, 10);

            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(String.valueOf(i + 1), x + 5, startY + 15);

            this.drawItemInSlot(g2d, x, startY, i);
        }
    }
    /**
     * Pomocná metóda vykresľujúca názov položky v danom slote inventára.
     *
     * @param g2d Grafický 2D kontext.
     * @param x X súradnica pre vykreslenie.
     * @param y Y súradnica pre vykreslenie.
     * @param slot Index slotu, ktorého položka sa kreslí.
     */
    private void drawItemInSlot(Graphics2D g2d, int x, int y, int slot) {
        String itemName = this.getItemNameForSlot(slot);
        if (itemName != null) {
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(itemName, x + 5, y + 30);
        }
    }
    /**
     * Vracia názov položky priradenej k danému slotu inventára.
     *
     * @param slot Index slotu.
     * @return Názov položky v slote alebo prázdny reťazec, ak slot nemá položku.
     */
    private String getItemNameForSlot(int slot) {
        return switch (slot) {
            case 0 -> "Hand";
            case 1 -> "Hammer";
            case 2 -> "Sword";
            case 3 -> "Bow";
            case 4 -> "Wall";
            case 5 -> "Canon";
            case 6 -> "";
            default -> null;
        };
    }
    /**
     * Nastaví vybraný slot inventára a zároveň podľa zvoleného slotu nastaví
     * hráčovu aktívnu zbraň a jej grafiku.
     *
     * @param slot Index slotu, ktorý má byť vybraný.
     */
    public void selectSlot(int slot) {
        if (slot >= 0 && slot < SLOT_COUNT) {
            this.selectedSlot = slot;

            switch (slot) {
                case 0:
                    this.player.setZbran(this.hand);
                    this.playerGraphics.setHandGraphics(this.handGraphics);
                    break;
                case 1:
                    this.player.setZbran(this.hammer);
                    this.playerGraphics.setHandGraphics(this.hammerGraphics);
                    break;
                case 2:
                    this.player.setZbran(this.sword);
                    this.playerGraphics.setHandGraphics(this.swordGraphics);
                    break;
                case 3:
                    this.player.setZbran(this.bow);
                    this.playerGraphics.setHandGraphics(this.bowGraphics);
                    break;
                case 4:
                default:
                    break;
            }
        }
    }
    /**
     * Získava index aktuálne vybraného slotu v inventári.
     *
     * @return Index vybraného slotu.
     */
    public int getSelectedSlot() {
        return this.selectedSlot;
    }
}