package gameManager;


import entity.Player;
import entity.Zombie;
import graphics.objectGraphics.CanonBallGraphics;
import graphics.objectGraphics.CanonGraphics;
import graphics.objectGraphics.StoneGraphic;
import graphics.objectGraphics.TreeGraphics;
import graphics.objectGraphics.WallGraphics;
import object.Canon;
import object.Material;
import object.Wall;
import projectile.CanonBall;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Trieda {@code ObjectManager} spravuje všetky herné objekty,
 * ako sú kamene, stromy, múry, kanóny a kanónové projektily.
 * Zabezpečuje ich generovanie, kolízne kontroly, vykresľovanie,
 * aktualizáciu a interakciu s hráčom a nepriateľmi.
 */
public class ObjectManager {

    private final ArrayList<StoneGraphic> stones ;
    private final ArrayList<TreeGraphics> trees;
    private final ArrayList<Wall> walls ;
    private final ArrayList<Canon> canons ;
    private final ArrayList<CanonBall> canonBalls ;

    public ObjectManager() {
        this.stones = new ArrayList<>();
        this.trees = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.canons = new ArrayList<>();
        this.canonBalls = new ArrayList<>();
    }
    /**
     * Generuje náhodné kamene a stromy v rámci určených hraníc mapy.
     *
     * @param numStones počet generovaných kameňov
     * @param numTrees počet generovaných stromov
     * @param maxCols maximálny počet stĺpcov na mape (pre rozsah náhodných súradníc)
     * @param maxRows maximálny počet riadkov na mape (pre rozsah náhodných súradníc)
     */
    public void generateObjects(int numStones, int numTrees, int maxCols, int maxRows) {
        Random rand = new Random();
        for (int i = 0; i < numStones; i++) {
            this.stones.add(new StoneGraphic(rand.nextInt(maxCols * 30), rand.nextInt(maxRows * 30)));
        }
        for (int i = 0; i < numTrees; i++) {
            this.trees.add(new TreeGraphics(rand.nextInt(maxCols * 30), rand.nextInt(maxRows * 30)));
        }
    }
    /**
     * Kontroluje, či hráč môže presunúť sa na dané súradnice.
     * Overuje kolízie s múrmi, kanónmi, kameňmi a stromami.
     *
     * @param x nová x-súradnica hráča
     * @param y nová y-súradnica hráča
     * @return {@code true}, ak je možné sa pohybovať, inak {@code false}
     */
    public boolean canMoveTo(int x, int y) {

        int playerSize = 50;

        Rectangle playerBounds = new Rectangle(x, y, playerSize, playerSize);
        for (int i = 0; i < playerSize; i += 10) {
            for (int j = 0; j < playerSize; j += 10) {
                int checkX = (x + i) / 20;
                int checkY = (y + j) / 20;
                for (Wall wall : this.walls) {
                    if (wall.getX() == checkX && wall.getY() == checkY) {
                        return false;
                    }
                }
                for (Canon canon : this.canons) {
                    if (canon.getX() == checkX && canon.getY() == checkY) {
                        return false;
                    }
                }
            }
        }
        for (StoneGraphic stone : this.stones) {
            if (playerBounds.intersects(stone.getBounds())) {
                return false;
            }
        }
        for (TreeGraphics tree : this.trees) {
            if (playerBounds.intersects(tree.getBounds())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return zoznam kameňov na mape
     */
    public ArrayList<StoneGraphic> getStones() {
        return this.stones;
    }
    /**
     * @return zoznam stromov na mape
     */
    public ArrayList<TreeGraphics> getTrees() {
        return this.trees;
    }
    /**
     * Pridá nový múr do hry.
     *
     * @param wall múr, ktorý sa má pridať
     */
    public void addWall(Wall wall) {
        this.walls.add(wall);
    }
    /**
     * Pridá nový kanón do hry.
     *
     * @param canon kanón, ktorý sa má pridať
     */
    public void addCanon(Canon canon) {
        this.canons.add(canon);
    }
    /**
     * Vykreslí všetky herné objekty (stromy, kamene, múry, kanóny a projektily).
     *
     * @param g grafický kontext na vykreslenie
     */
    public void drawObjects(Graphics g) {
        for (TreeGraphics tree : this.trees) {
            tree.drawTree(g);
        }

        for (StoneGraphic stone : this.stones) {
            stone.drawStone(g);
        }

        WallGraphics wallGraphics = new WallGraphics();
        for (Wall wall : this.walls) {
            wallGraphics.getWallGrafics(g, wall.getX(), wall.getY());
        }
        CanonGraphics canonGraphics = new CanonGraphics();
        for (Canon canon : this.canons) {
            canonGraphics.getCanonGraphics(g, canon.getX() , canon.getY() , canon.getAngle());
        }

        CanonBallGraphics canonBallGraphics = new CanonBallGraphics();
        for (CanonBall canonBall : this.canonBalls) {
            canonBallGraphics.draw(g, canonBall);
        }
    }
    /**
     * Odstráni stromy, ktoré sú v dosahu útoku hráča.
     *
     * @param player hráč, ktorý útočí
     */
    public void removeDestroyedTrees(Player player) {
        int hitRange = player.getWeapon().getRange();
        int size = 50;

        Rectangle hitBox = new Rectangle(
                player.getX() - hitRange,
                player.getY() - hitRange,
                size + 2 * hitRange,
                size + 2 * hitRange
        );

        this.trees.removeIf(t -> t.getBounds().intersects(hitBox));
    }
    /**
     * Odstráni kamene, ktoré sú v dosahu útoku hráča.
     *
     * @param player hráč, ktorý útočí
     */
    public void removeDestroyedStones(Player player) {
        int hitRange = player.getWeapon().getRange();
        int size = 50;

        Rectangle hitBox = new Rectangle(
                player.getX() - hitRange,
                player.getY() - hitRange,
                size + 2 * hitRange,
                size + 2 * hitRange
        );

        this.stones.removeIf(s -> s.getBounds().intersects(hitBox));
    }
    /**
     * Skontroluje, či je možné zasiahnuť nejaký materiál (kameň alebo drevo)
     * na daných súradniciach v dosahu hráča.
     *
     * @param x x-súradnica útoku
     * @param y y-súradnica útoku
     * @param player hráč, ktorý útočí
     * @return materiál, ktorý je možné zasiahnuť, alebo {@code null}, ak žiadny nie je
     */
    public Material canHit(int x, int y , Player player) {

        int playerSize = 50;
        int hitRange = player.getWeapon().getRange();

        Rectangle playerBounds = new Rectangle(x - hitRange, y - hitRange, playerSize + 2 * hitRange, playerSize + 2 * hitRange);

        for (StoneGraphic stone : this.stones) {
            if (playerBounds.intersects(stone.getBounds())) {
                return stone.getStone();
            }
        }

        for (TreeGraphics tree : this.trees) {
            if (playerBounds.intersects(tree.getBounds())) {
                return tree.getWood();
            }
        }
        return null;
    }
    /**
     * Overí, či je možné postaviť kanón na dané súradnice.
     * Kanón nemožno postaviť na mieste kolidujúcom s hráčom, múrmi, kameňmi alebo stromami.
     *
     * @param x x-súradnica na mriežke (20x20 pixlov)
     * @param y y-súradnica na mriežke (20x20 pixlov)
     * @param player hráč (pre kontrolu kolízie)
     * @return {@code true}, ak je možné kanón postaviť, inak {@code false}
     */
    public boolean canPlaceCanon(int x, int y , Player player) {
        Rectangle canonArea = new Rectangle(x * 20, y * 20, 40, 40);

        if (new Rectangle(player.getX(), player.getY(), 50, 50).intersects(canonArea)) {
            return false;
        }

        for (Wall wall : this.walls) {
            if (new Rectangle(wall.getX() * 20, wall.getY() * 20, 20, 20).intersects(canonArea)) {
                return false;
            }
        }

        for (StoneGraphic stone : this.stones) {
            if (stone.getBounds().intersects(canonArea)) {
                return false;
            }
        }

        for (TreeGraphics tree : this.trees) {
            if (tree.getBounds().intersects(canonArea)) {
                return false;
            }
        }

        return true;
    }
    /**
     * Aktualizuje všetky kanónové projektily a odstraňuje tie, ktoré sa majú vymazať.
     */
    public void updateCanonBalls() {
        for ( CanonBall canonBall : this.canonBalls) {
            canonBall.update();
        }
        this.canonBalls.removeIf(CanonBall::shouldRemove);
    }
    /**
     * Aktualizuje všetky kanóny, ich stav a projektily.
     * Tiež kontroluje kolízie projektilov s nepriateľmi (zombie).
     *
     * @param zombies zoznam zombie, ktoré môžu byť zasiahnuté projektilmi
     */
    public void updateCanons(ArrayList<Zombie> zombies) {
        this.canons.removeIf(Canon::isDestroyed);


        for (Canon canon : this.canons) {
            canon.update(zombies);

            for (CanonBall ball : canon.getProjectiles()) {
                if (!this.canonBalls.contains(ball)) {
                    this.canonBalls.add(ball);
                }
            }
        }


        Iterator<CanonBall> ballIter = this.canonBalls.iterator();
        while (ballIter.hasNext()) {
            CanonBall ball = ballIter.next();
            ball.update();

            Iterator<Zombie> zombieIter = zombies.iterator();
            while (zombieIter.hasNext()) {
                Zombie zombie = zombieIter.next();
                if (new Rectangle((int)ball.getX() - 5, (int)ball.getY() - 5, 10, 10).intersects(new Rectangle(zombie.getX(), zombie.getY(), 50, 50))) {
                    zombie.decreaseHp(ball.getDamage());
                    if (zombie.isDead()) {
                        zombieIter.remove();
                    }
                    ballIter.remove();
                    break;
                }
            }

            if (ball.shouldRemove()) {
                ballIter.remove();
            }
        }
    }
}
