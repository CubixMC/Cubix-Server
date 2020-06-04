package org.cubixmc.server;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.logging.Level;

public class CubixTerminal {
    private Terminal terminal;
    private boolean running;

    public void start() {
        try {
            this.terminal = TerminalBuilder.builder()
//                    .system(false)
//                    .streams(System.in, System.out)
                    .jansi(true)
                    .build();
            new Thread(this::run).start();
        } catch (IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to start terminal reader", e);
        }
    }

    private void run() {
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        this.running = true;
        while(running) {
            try {
                String line = reader.readLine("> ");
                if(line.equalsIgnoreCase("stop")) {
                    CubixServer.getInstance().stop();
                }
            } catch (UserInterruptException e) {
                // ignore
            } catch (EndOfFileException e) {
                return;
            }
        }
    }

    public void stop() {
        this.running = false;
        try {
            terminal.close();
        } catch (IOException e) {
            CubixServer.getLogger().log(Level.WARNING, "Failed to stop terminal reader", e);
        }
    }
}
