package effects;

import entities.player.Player;

public abstract class Effects {
    private final long duration;
    private long startTime;
    private final String name;
    private boolean isActive;

    public Effects(long duration , String name) {
        this.isActive = false;
        this.name = name;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }
    public String getName() {
        return this.name;
    }
    public long getRemainingTime() {
        long time = System.currentTimeMillis() - this.startTime;
        return  this.duration - time;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.startTime >= this.duration;
    }
    protected void setActivateState(boolean activate) {
        this.isActive = activate;
    }
    protected boolean isActive() {
        return  this.isActive;
    }

    public abstract void useEffect(Player player) ;
    public abstract void removeEffect(Player player);
}
