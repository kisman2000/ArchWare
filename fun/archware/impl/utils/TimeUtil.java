package fun.archware.impl.utils;

/**
 * Created by 1 on 01.04.2021.
 */
public class TimeUtil {

    private long lastMS = 0;

    public TimeUtil() {
        this.reset();
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(final float milliSec) {
        return this.getTime() - this.lastMS >= milliSec;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

}
