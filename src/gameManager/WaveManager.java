package gameManager;

import gameManager.entityManager.EnemyManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Trieda {@code WaveManager} spravuje vlny nepriateľov v hre.
 * Postupne vytvára nepriateľov rôznych typov v pravidelných intervaloch,
 * pričom počet nepriateľov vo vlnách sa zvyšuje s každou novou vlnou.
 * Demonštruje polymorfizmus - pracuje s rozhraním EnemyManager bez potreby vedieť konkrétne typy.
 */
public class WaveManager {
    private int currentWave = 0;
    private Timer waveTimer;

    private final EnemyManager enemyManager;

    private final int cols;
    private final int rows;

    /**
     * Konštruktor triedy WaveManager.
     * Inicializuje manažér nepriateľov a predgeneruje základnú skupinu.
     *
     * @param enemyManager manažér nepriateľov (polymorfne spravuje všetky typy)
     * @param cols počet stĺpcov mapy
     * @param rows počet riadkov mapy
     */
    public WaveManager(EnemyManager enemyManager, int cols, int rows) {
        this.enemyManager = enemyManager;
        this.enemyManager.createEnemies(5, cols, rows);
        this.cols = cols;
        this.rows = rows;
    }

    /**
     * Spustí generovanie vĺn nepriateľov.
     * Vlny sa vytvárajú v pravidelných intervaloch (30 sekúnd),
     * pričom počet nepriateľov vo vlnách sa náhodne zvyšuje podľa aktuálneho čísla vlny.
     * Polymorfizmus - jeden manažér spravuje všetky typy nepriateľov.
     */
    public void startWaves() {
        this.waveTimer = new Timer();
        this.waveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                WaveManager.this.currentWave++;
                Random rand = new Random();
                int enemyCount = rand.nextInt(1 + WaveManager.this.currentWave);
                WaveManager.this.enemyManager.createEnemies(enemyCount, WaveManager.this.cols, WaveManager.this.rows);
            }
        }, 0, 10_000);
    }

    /**
     * Zastaví generovanie nových vĺn nepriateľov.
     * Zruší timer, ktorý riadi spúšťanie nových vĺn.
     */
    public void stopWaves() {
        if (this.waveTimer != null) {
            this.waveTimer.cancel();
        }
    }
}
