package org.cubixmc.plugin;

import com.sun.tools.javac.util.List;
import lombok.Getter;
import org.cubixmc.Server;

import java.util.Collection;

public abstract class Plugin {

    private @Getter Server server;
    private @Getter List<Object> commands;

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
        return getServer().getOnlinePlayers();
    }

}
