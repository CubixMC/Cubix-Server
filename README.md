# Cubix-Server
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=CubixMC_Cubix-Server&metric=alert_status)](https://sonarcloud.io/dashboard?id=CubixMC_Cubix-Server)

Cubix MC is an open-source minecraft sever. This project under development, 
and most functionality has not yet been implemented. This project is intended for experimental usage only.

Cubix MC exists mostly because it is fun to work on. 
The intention is to make a minecraft server that implements the latest Paper API,
is lightweight, modular and performs much better than Vanilla.  
Cubix MC does not attempt to create a vanilla experience, and is only suitable for mini-games and lobbies.

The current version is built for Minecraft 1.8.x.  
When the Paper API has been implemented for the most part, I will start working on 1.15.2 support.  
CubixMC does support online-mode and bungeecord ip-forwarding out of the box.
It also works with ViaVersion is installed through BungeeCord.

## Installation
**Warning:** Cubix MC is in early development, and only intended for experimentation.  
You can get the latest nightly build at [the build server](https://ci.codemc.io/job/lenis0012/job/Cubix-Server/).

Before running it, make sure that you have a 1.8.8 pre-generated world available, 
because Cubix does not support vanilla world generation.
The simplest way to do this is by running a vanilla (or spigot) 1.8.8 server and generating a world.
Copy the world folder to the folder where you put your cubix jar file.

Once you have a world ready, run `java -jar cubix-server-0.1.0-SNAPSHOT.jar`.  
This will start a minecraft server on port 25565. The port can not be changed currently.

To stop your server gracefully, run `stop` in the terminal, and wait 3-5 seconds.
Logs are saved to the logs directory, similarly to vanilla servers.

## Development
Cubix MC uses maven to manage dependencies.  
To build the server jar, run `mvn clean package`, and look for `cubix-server/target/cubix-server-VERSION.jar`