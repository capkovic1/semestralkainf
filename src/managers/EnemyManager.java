package managers;

import entities.Entity;
import entities.enemies.Enemy;
import entities.enemies.Frog;
import entities.enemies.SpearThrower;
import entities.enemies.Wolf;
import entities.enemies.Zombie;
import entities.player.Player;
import graphics.infoGraphics.DamageIndicator;

import resource.ResourceType;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Manažér všetkých nepriateľov v hre.
 *
 * Udržiava zoznam entít typu {@code Enemy}, ich grafiky a damage indikátory.
 * Zodpovedá za vytváranie nepriateľov, aktualizáciu ich stavu, vykresľovanie
 * a pridávanie odkazov na zlato hráčovi pri zabití nepriateľa.
 */
public class EnemyManager {
    private final ArrayList<Enemy> enemyList;
    private final ArrayList<DamageIndicator> damageIndicators;

    /**
     * Vytvorí nový manažér nepriateľov s prázdnymi zoznamami.
     */
    public EnemyManager() {
        this.enemyList = new ArrayList<>();
        this.damageIndicators = new ArrayList<>();
    }

    /**
     * Vytvorí daný počet nepriateľov na náhodných pozíciách v hraniciach (cols, rows).
     * Výber konkrétneho typu nepriateľa je náhodný.
     *
     * @param count počet vytváraných nepriateľov
     * @param cols šírka mapy (v jednotkách pre generovanie pozícií)
     * @param rows výška mapy (v jednotkách pre generovanie pozícií)
     */
    public void createEnemies(int count, int cols, int rows) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(cols * 30);
            int y = rand.nextInt(rows * 30);

            int type = rand.nextInt(4);
            Enemy enemy = switch (type) {
                case 0 -> new Wolf(100, 1, x, y, 4);
                case 1 -> new SpearThrower(200, 5, x, y, 0);
                case 2 -> new Frog(150, 1, x, y, 200);
                default -> new Zombie(300, 2, x, y, 2);
            };

            this.enemyList.add(enemy);
        }
    }

    /**
     * Aktualizuje stav všetkých nepriateľov (útoky, kolízie, odstraňovanie mŕtvych).
     * Ak nepriateľ zomrie, hráč získa odmenu v zlate a nepriateľ aj jeho grafika sa odstránia.
     *
     * @param player Inštancia hráča, pre ktorého sa spracúvajú útoky a odmeny
     * @return {@code true}, ak aspoň jeden nepriateľ zomrel počas tejto aktualizácie
     */
    public boolean updateEnemies(Player player) {
        Iterator<Enemy> enemyIterator = this.enemyList.iterator();

        boolean someoneDied = false;

        this.damageIndicators.removeIf(DamageIndicator::isExpired);

        while (enemyIterator.hasNext() ) {
            Enemy enemy = enemyIterator.next();

            if (enemy.isDead()) {
                player.addResource(ResourceType.GOLD, enemy.getGoldReward());
                enemyIterator.remove();
                someoneDied = true;
                continue;
            }

            int targetX = player.getX() + 25;
            int targetY = player.getY() + 25;

            enemy.attack(targetX, targetY);

            if (CollisionDetector.checkPlayerCollidesWithEnemy(player, enemy)) {
                player.takeDamage(enemy.getDamage());
            }
        }
        return someoneDied;
    }

    /**
     * Vykreslí všetkých nepriateľov a damage indikátory na zadanom grafickom kontexte.
     * Ak je nepriateľ mŕtvy, vykreslí sa jeho death efekt namiesto bežného vykreslenia.
     *
     * @param g grafický kontext použitý na vykreslenie
     */
    public void drawEnemies(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        for (DamageIndicator indicator : this.damageIndicators) {
            indicator.draw(g2d);
        }
        for (Enemy enemy : this.enemyList) {
            if (!enemy.isDead()) {
                enemy.getGraphics().draw(g);
            } else {
                enemy.getGraphics().drawDeathEffect(g);
            }
        }
    }

    /**
     * Pridá indikátor poškodenia pre danú entitu (pre zobrazenie damage textu nad entitou).
     *
     * @param entity entita, ktorá utrpela poškodenie
     * @param damage množstvo poškodenia
     */
    public void addDamageIndicator(Entity entity, int damage) {
        this.damageIndicators.add(new DamageIndicator(entity.getX() + 25, entity.getY(), damage));
    }

    /**
     * Vyčistí zoznam nepriateľov a súvisiacich grafík (použité pri resetovaní hry).
     */
    public void clearEnemies() {
        this.enemyList.clear();
    }

    /**
     * Vráti zoznam aktívnych nepriateľov.
     *
     * @return modifikovateľný {@code ArrayList} nepriateľov
     */
    public ArrayList<Enemy> getEnemyList() {
        return this.enemyList;
    }
}
