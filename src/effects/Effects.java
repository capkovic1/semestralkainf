package effects;

import entities.player.Player;

/**
 * Abstraktná trieda reprezentujúca efekt aplikovaný na hráča.
 * Obsahuje základnú logiku trvania efektu, aktivácie a názvu efektu.
 */
public abstract class Effects {
    private final long duration;
    private long startTime;
    private final String name;
    private boolean isActive;

    /**
     * Vytvorí efekt s danou dĺžkou trvania a názvom.
     *
     * @param duration dĺžka trvania efektu v milisekundách
     * @param name     názov efektu
     */
    public Effects(long duration , String name) {
        this.isActive = false;
        this.name = name;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Získá názov efektu.
     *
     * @return názov efektu
     */
    public String getName() {
        return this.name;
    }

    /**
     * Vracia zostávajúci čas (v ms) do skončenia efektu.
     * Hodnota môže byť záporná, ak už efekt prekročil trvanie.
     *
     * @return zostávajúci čas v milisekundách
     */
    public long getRemainingTime() {
        long time = System.currentTimeMillis() - this.startTime;
        return  this.duration - time;
    }

    /**
     * Nastaví čas začiatku efektu (napr. pri re-aktivácii).
     *
     * @param startTime čas začiatku v milisekundách (System.currentTimeMillis())
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Kontroluje, či efekt už vypršal podľa aktuálneho času.
     *
     * @return {@code true} ak efekt vypršal, inak {@code false}
     */
    public boolean isExpired() {
        return System.currentTimeMillis() - this.startTime >= this.duration;
    }

    /**
     * Nastaví interný stav aktivácie efektu.
     * Protected aby ho mohli meniť podtriedy.
     *
     * @param activate {@code true} ak sa efekt aktivuje, inak {@code false}
     */
    protected void setActivateState(boolean activate) {
        this.isActive = activate;
    }

    /**
     * Zisťuje, či je efekt aktuálne aktívny.
     * Protected pre použitie v podtriedach.
     *
     * @return {@code true} ak je aktívny, inak {@code false}
     */
    protected boolean isActive() {
        return  this.isActive;
    }

    /**
     * Aplikuje efekt na hráča (napríklad zmena štatov) a nastaví potrebné polia.
     * Implementuje ho každá konkrétna podtrieda efektu.
     *
     * @param player hráč, na ktorého sa efekt aplikuje
     */
    public abstract void useEffect(Player player) ;

    /**
     * Odstráni efekt z hráča (obnoví pôvodné hodnoty).
     * Implementuje ho každá konkrétna podtrieda efektu.
     *
     * @param player hráč, z ktorého sa efekt odstraňuje
     */
    public abstract void removeEffect(Player player);
}
