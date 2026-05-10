package gameManager.entityManager;

import entity.*;
import graphics.infoGraphics.DamageIndicator;
import graphics.entityGraphics.*;
import projectile.Spear;
import resource.ResourceType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EnemyManager {
    private final ArrayList<Enemy> enemyList;
    private final ArrayList<EnemyGraphics> graphicsList;
    private final ArrayList<DamageIndicator> damageIndicators;

    public EnemyManager() {
        this.enemyList = new ArrayList<>();
        this.graphicsList = new ArrayList<>();
        this.damageIndicators = new ArrayList<>();
    }

    public void createEnemies(int count, int cols, int rows) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(cols * 30);
            int y = rand.nextInt(rows * 30);

            int type = rand.nextInt(4);
            Enemy enemy;

            switch (type) {
                case 0:
                    enemy = new Wolf(100, 1, x, y, 4);
                    break;
                case 1:
                    enemy = new Zombie(300, 2, x, y, 2);
                    break;
                case 2:
                    enemy = new Frog(150, 1, x, y, 200);
                    break;
                case 3:
                    enemy = new SpearThrower(200, 5, x, y, 0);
                    break;
                default:
                    enemy = new Zombie(300, 2, x, y, 2);
            }

            EnemyGraphics graphics = enemy.getGraphics();

            this.enemyList.add(enemy);
            this.graphicsList.add(graphics);
        }
    }

    public boolean updateEnemies(Player player) {
        Iterator<Enemy> enemyIterator = this.enemyList.iterator();
        Iterator<EnemyGraphics> graphicsIterator = this.graphicsList.iterator();
        boolean someoneDied = false;

        this.damageIndicators.removeIf(DamageIndicator::isExpired);

        while (enemyIterator.hasNext() && graphicsIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            EnemyGraphics graphics = graphicsIterator.next();

            if (enemy.isDead()) {
                player.addResource(ResourceType.GOLD,enemy.getGoldReward());
                enemyIterator.remove();
                graphicsIterator.remove();
                someoneDied = true;
                 continue;
            }

            int targetX = player.getX() + 25;
            int targetY = player.getY() + 25;

            enemy.attack(targetX, targetY);

            if (enemy instanceof SpearThrower) {
                SpearThrower st = (SpearThrower)enemy;
                st.updateSpears();

                Rectangle playerRect = new Rectangle(player.getX(), player.getY(), 50, 50);
                for (Spear spear : st.getSpears()) {
                    Rectangle spearRect = new Rectangle(spear.getX(), spear.getY(), 10, 10);
                    if (spearRect.intersects(playerRect) && spear.isActive()) {
                        player.takeDamage(spear.getDamage());
                        spear.throwSpear(spear.getX(), spear.getY());
                    }
                }
            }

            Rectangle playerRect = new Rectangle(player.getX(), player.getY(), 50, 50);
            Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 50, 50);

            if (playerRect.intersects(enemyRect)) {
                player.takeDamage(enemy.getDamage());
            }
        }
        return someoneDied;
    }

    public void drawEnemies(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (DamageIndicator indicator : this.damageIndicators) {
            indicator.draw(g2d);
        }
        for (int i = 0; i < this.enemyList.size(); i++) {
            Enemy enemy = this.enemyList.get(i);
            EnemyGraphics graphics = this.graphicsList.get(i);

            if (!enemy.isDead()) {
                graphics.draw(g);

                if (enemy instanceof SpearThrower) {
                    SpearThrower st = (SpearThrower)enemy;
                    for (Spear spear : st.getSpears()) {
                        if (spear.isActive()) {
                            new SpearGraphics(spear).draw(g);
                        }
                    }
                }
            } else {
                graphics.drawDeathEffect(g);
            }
        }
    }

    public void addDamageIndicator(int x, int y, int damage) {
        this.damageIndicators.add(new DamageIndicator(x, y, damage));
    }

    public void clearEnemies() {
        this.enemyList.clear();
        this.graphicsList.clear();
    }

    public ArrayList<Enemy> getEnemyList() {
        return this.enemyList;
    }
}
