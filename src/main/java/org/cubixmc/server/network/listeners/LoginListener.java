package org.cubixmc.server.network.listeners;


public class LoginListener extends PacketListener{

    public LoginListener(){
        super();
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
