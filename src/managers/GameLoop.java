package managers;

import entities.player.Player;

import javax.swing.*;


public class GameLoop {
    private Timer gameTimer;
    private final GameManager gameManager;
    private GameInputHandler inputHandler;


    private final Player player;
    private long lastRegenTime = System.currentTimeMillis();
    private long objectTimer = System.currentTimeMillis();
    private long startTime;

    private final Runnable onRepaint;
    private final Runnable onGameOver;

    private final int rows;
    private final int cols;

    public GameLoop(GameManager gameManager, GameInputHandler inputHandler, Runnable onRepaint, Runnable onGameOver, long startTime , int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.gameManager = gameManager;
        this.player = gameManager.getPlayer();
        this.inputHandler = inputHandler;
        this.onRepaint = onRepaint;
        this.onGameOver = onGameOver;
        this.startTime = startTime;

        this.initTimer();
    }
    private void initTimer() {
        this.gameTimer = new Timer(20, e -> this.update());
    }
    private void update() {
        this.inputHandler.update();

        if (this.player.getHealth() <= 0) {
            this.gameTimer.stop();
            GameHistory.saveHistory(this.player.getScore(), this.startTime);
            this.onGameOver.run();
            return;
        }


        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastRegenTime >= 1000) {
            this.regeneratePlayer();
            this.lastRegenTime = currentTime;
        }

        if (currentTime - this.objectTimer >= 30000) {
            this.gameManager.getObjectManager().generateObjects(1, 1 , this.cols , this.rows);
            this.objectTimer = currentTime;
        }

        this.gameManager.updateObjects();
        ProjectileManager.getInstance().updateProjectiles(this.gameManager.getEnemyManager().getEnemyList(), this.gameManager.getEnemyManager() , this.player);

        if (this.gameManager.updateEntities()) {
            this.gameManager.getEffectsManager().addRandomEffect();
        }
        this.gameManager.updateEffects();

        this.onRepaint.run();
    }
    public void regeneratePlayer() {
        if (this.player.getHealth() < this.player.getMaxHealth()) {
            this.player.setHealth(this.player.getHealth() + 1);
        }
    }
    public void start() {
            this.gameTimer.start();
    }
    public void stop() {
        if (this.gameTimer != null && this.gameTimer.isRunning()) {
            this.gameTimer.stop();
        }
    }
    public void restart(GameInputHandler inputHandler, long newStartTime) {
        this.stop();
        this.inputHandler = inputHandler;
        this.startTime = newStartTime;
        this.lastRegenTime = System.currentTimeMillis();
        this.objectTimer = System.currentTimeMillis();
        this.gameTimer = new Timer(20, e -> this.update());
        this.start();
    }
}
