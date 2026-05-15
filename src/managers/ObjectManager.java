package managers;


import entities.enemies.Enemy;
import entities.player.Player;
import object.material.Material;
import object.material.Stone;
import object.material.Tree;
import object.structure.Structure;
import projectile.Projectile;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda {@code ObjectManager} spravuje všetky herné objekty,
 * ako sú kamene, stromy, múry, kanóny a kanónové projektily.
 * Zabezpečuje ich generovanie, kolízne kontroly, vykresľovanie,
 * aktualizáciu a interakciu s hráčom a nepriateľmi.
 */
public class ObjectManager {

    private final ArrayList<Material> materials;
    private final ArrayList<Structure> structures ;


    public ObjectManager() {
        this.materials = new ArrayList<>();
        this.structures = new ArrayList<>();
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
            this.materials.add(new Stone(100, rand.nextInt(maxCols * 30), rand.nextInt(maxRows * 30)));
        }
        for (int i = 0; i < numTrees; i++) {
            this.materials.add(new Tree(100 , rand.nextInt(maxCols * 30), rand.nextInt(maxRows * 30)));
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
        return CollisionDetector.checkPlayerCanMoveTo(x, y, this.structures, this.materials);
    }

    /**
     * Vykreslí všetky herné objekty (stromy, kamene, múry, kanóny a projektily).
     *
     * @param g grafický kontext na vykreslenie
     */
    public void drawObjects(Graphics g) {
        for (Material material : this.materials) {
            material.draw(g);
        }
        for (Structure structure : this.structures) {
            structure.draw(g);
        }
        for (Projectile projectile : ProjectileManager.getInstance().getProjectiles()) {
            projectile.draw(g);
        }

    }

    /**
     * Odstráni zničené materiály v okolí hráča na základe jeho dosahu zbrane.
     *
     * @param player hráč, ktorý ničí materiály
     */
    public void removeDestroyedMaterial(Player player) {
        int hitRange = player.getWeapon().getRange();
        int size = 50;

        Rectangle hitBox = new Rectangle(player.getX() - hitRange, player.getY() - hitRange, size + 2 * hitRange, size + 2 * hitRange);

        this.materials.removeIf(material -> material.getBounds().intersects(hitBox));
    }

    /**
     * Skontroluje, či je možné zasiahnuť nejaký materiál (kameň alebo drevo)
     * na daných súradniciach v dosahu hráča.
     *
     * @param player hráč, ktorý útočí
     * @return materiál, ktorý je možné zasiahnuť, alebo {@code null}, ak žiadny nie je
     */
    public Material getHitableMaterial(Player player) {
        return CollisionDetector.getHitMaterial(player.getX(), player.getY(), player.getWeapon().getRange(), this.materials);
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
    public boolean canPlaceStructure(int x, int y , Player player) {
        return CollisionDetector.checkCanPlaceStructure(x, y, player, this.structures, this.materials);
    }

    /**
     * Aktualizuje všetky štruktúry na mape, vrátane kanónov a nepriateľov.
     * Odstraňuje zničené štruktúry a aktualizuje projektily.
     *
     * @param enemies zoznam nepriateľov na mape
     * @param enemyManager manažér nepriateľov na správu ich stavu a správania
     * @param player hráč, ktorý ovláda kanóny
     */
    public void updateStructures(ArrayList<Enemy> enemies , EnemyManager enemyManager , Player player) {
        this.structures.removeIf(Structure::isDestroyed);

        for (Structure structure : this.structures) {
            structure.update(enemies);
        }
        ProjectileManager.getInstance().updateProjectiles(enemies, enemyManager, player);
    }

    /**
     * Obnoví stav manažéra objektov, odstráni všetky materiály a štruktúry.
     */
    public void reset() {
        this.materials.clear();
        this.structures.clear();
    }
    /**
     * Pridá novú štruktúru (napr. kanón) do zoznamu štruktúr na mape.
     *
     * @param structure štruktúra, ktorá sa má pridať
     */
    public void addStructure(Structure structure) {
        this.structures.add(structure);
    }
}
