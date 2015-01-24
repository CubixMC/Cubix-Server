package org.cubixmc.plugin;

import org.cubixmc.Server;

import java.util.Collection;
import java.util.List;

public abstract class Plugin {

    private Server server;
    private List<Object> commands;

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract boolean onCommand(Object sender, Object Command, String[] args);



    //TODO Add Command Register
    public void register(Object cmd){
        commands.add(cmd);
    }

    //TODO Add Event Register
    public void register(Object listener, Plugin plugin){

    }

    public Collection<?> getOnlinePlayers(){
        return server.getOnlinePlayers();
    }

}
