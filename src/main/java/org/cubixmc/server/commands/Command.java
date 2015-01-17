package org.cubixmc.server.commands;


import lombok.Getter;
import org.cubixmc.server.CubixServer;

import java.util.List;

public class Command {
    private @Getter String label;
    private @Getter List<String> permissions;

    private static CubixServer instance = CubixServer.getInstance();

    public static void registerCmd(Command cmd){
        instance.getCommands().add(cmd);
    }

}
