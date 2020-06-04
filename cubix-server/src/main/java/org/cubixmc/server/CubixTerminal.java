package org.cubixmc.server;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.terminal.Terminal;

public class CubixTerminal extends SimpleTerminalConsole {
    private boolean running = true;

    @Override
    protected boolean isRunning() {
        return running;
    }

    @Override
    protected void runCommand(String command) {
        System.out.println("Received " + command);
        if(command.equalsIgnoreCase("stop")) {
            CubixServer.getInstance().stop();
        }
    }

    @Override
    protected void shutdown() {
        CubixServer.getInstance().stop();
    }

    public void stop() {
        this.running = false;
    }
}
