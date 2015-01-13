package org.cubixmc.world;


public class WorldUtil {
private long time;

private boolean pvp;

private int spawnanimallimit;
private int spawnmonsterlimit;


private String[] getGameRules;

public long getTime() {
return time;
}

public void setTime(long time) {
this.time = time;
}
public boolean isPVPEnabled() {
return pvp;
}
public void setPVPEnabled(boolean bool) {
this.pvp = bool;

 }
public int getSpawnAnimalLimit() {
return spawnanimallimit;
}
public void setSpawnAnimalLimit(int spawnLimit) {
this.spawnanimallimit = spawnLimit;
}
public String[] getGameRules() { 
return getGameRules;
}

public int getSpawnMonsterLimit() {
return spawnmonsterlimit;
}
public void setSpawnMonsterLimit(int spawnLimit) {
this.spawnmonsterlimit = spawnLimit;
}

}