package gameManager;


import entity.Enemy;
import entity.Player;
import gameManager.entityManager.EnemyManager;
import graphics.objectGraphics.*;
import object.structure.Canon;
import object.material.Material;
import object.structure.Structure;
import object.structure.Wall;
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

    private final ArrayList<MaterialGraphics> materials;

    private final ArrayList<Wall> walls ;
    private final ArrayList<Canon> canons ;

    private final ArrayList<Structure> structures ;

    private final ArrayList<CanonBall> canonBalls ;

    public ObjectManager() {
        this.materials = new ArrayList<>();
        this.structures = new ArrayList<>();
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
            this.materials.add(new StoneGraphic(rand.nextInt(maxCols * 30), rand.nextInt(maxRows * 30)));
        }
        for (int i = 0; i < numTrees; i++) {
            this.materials.add(new TreeGraphics(rand.nextInt(maxCols * 30), rand.nextInt(maxRows * 30)));
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


                for (Structure structure : this.structures) {
                    if (structure.getX() == checkX && structure.getY() == checkY) {
                        return false;
                    }
                }
            }
        }
        for (MaterialGraphics material : this.materials) {
            if (playerBounds.intersects(material.getBounds())) {
                return false;
            }
        }

        return true;
    }


    /**
     * Pridá nový múr do hry.
     *
     * @param wall múr, ktorý sa má pridať
     */
    public void addWall(Wall wall) {
        this.structures.add(wall);
        this.walls.add(wall);
    }
    /**
     * Pridá nový kanón do hry.
     *
     * @param canon kanón, ktorý sa má pridať
     */
    public void addCanon(Canon canon) {
        this.structures.add(canon);
        this.canons.add(canon);
    }
    /**
     * Vykreslí všetky herné objekty (stromy, kamene, múry, kanóny a projektily).
     *
     * @param g grafický kontext na vykreslenie
     */
    public void drawObjects(Graphics g) {
        for (MaterialGraphics material : this.materials) {
            material.draw(g);
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

    public void removeDestroyedMaterial(Player player) {
        int hitRange = player.getWeapon().getRange();
        int size = 50;

        Rectangle hitBox = new Rectangle(
                player.getX() - hitRange,
                player.getY() - hitRange,
                size + 2 * hitRange,
                size + 2 * hitRange
        );
        this.materials.removeIf(material -> material.getBounds().intersects(hitBox));
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

        for (MaterialGraphics material : this.materials) {
            if (playerBounds.intersects(material.getBounds())) {
                return material.getMaterial();
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
        for (Structure structure : this.structures) {
            if (new Rectangle(structure.getX() * 20, structure.getY() * 20, 20, 20).intersects(canonArea)) {
                return false;
            }
        }

        for (MaterialGraphics material : this.materials) {
            if (material.getBounds().intersects(canonArea)) {
                return false;
            }
        }

        return true;
    }
    /**
     * Aktualizuje všetky kanóny, ich stav a projektily.
     * Tiež kontroluje kolízie projektilov s nepriateľmi (zombie).
     *
     * @param enemies zoznam nepriateľov, ktoré môžu byť zasiahnuté projektilmi
     */
    public void updateCanons(ArrayList<Enemy> enemies, EnemyManager enemyManager) {
        this.canons.removeIf(Canon::isDestroyed);

        for (Canon canon : this.canons) {
            canon.update(enemies);

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

            for (Enemy enemy : enemies) {
                if (new Rectangle((int)ball.getX() - 5, (int)ball.getY() - 5, 10, 10).intersects(new Rectangle(enemy.getX(), enemy.getY(), 50, 50))) {
                    int damage = ball.getDamage();
                    enemy.decreaseHp(damage);
                    enemyManager.addDamageIndicator(enemy.getX() + 25, enemy.getY(), damage);
                    ballIter.remove();
                    break;
                }
            }

            if (ball.shouldRemove()) {
                ballIter.remove();
            }
        }
    }
    public void reset() {
        this.materials.clear();
        this.structures.clear();
        this.walls.clear();
        this.canons.clear();
        this.canonBalls.clear();
    }
}
