package org.cubixmc.server.threads;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class Threads {
    /**
     * Main thread tries to reach 20 TPS.
     */
    public static final MainThread mainThread = new MainThread(20);
    /**
     * World thread, chunk loading, saving and generating.
     */
    public static final ExecutorService worldExecutor = Executors.newFixedThreadPool(2);
    /**
     * Pathfinding thread
     */
    public static final ExecutorService pathExecutor = Executors.newFixedThreadPool(1);
    public static final ExecutorService playerExecutor = Executors.newFixedThreadPool(2);
}
