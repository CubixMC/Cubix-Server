package org.cubixmc.server.threads;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public class Threads {
    /**
     * Main thread tries to reach 20 TPS.
     */
//    public static final MainThread mainThread = new MainThread(20);
    public static final ScheduledExecutorService mainThread = Executors.newSingleThreadScheduledExecutor();
    /**
     * World thread, chunk loading, saving and generating.
     */
    public static final ExecutorService worldExecutor = Executors.newFixedThreadPool(3);
    /**
     * Entity executor, AI and such
     */
    public static final ExecutorService entityExecutor = Executors.newFixedThreadPool(2);
}
