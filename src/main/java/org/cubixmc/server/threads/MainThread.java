package org.cubixmc.server.threads;

import org.cubixmc.server.CubixServer;

public class MainThread extends Thread {
    private final int ticksPerSecond;

    public MainThread(int ticksPerSecond) {
        super("Cubix main thread");
        this.ticksPerSecond = ticksPerSecond;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        CubixServer.getInstance().tick();

        long duration = System.currentTimeMillis() - start;
        long sleepTime = 1000L / ticksPerSecond - duration;
        if(sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
                // TODO: Logger
                e.printStackTrace();
            }
        }
    }
}
