package org.cubixmc.server;

import java.util.logging.Logger;


public class CubixLogger {

private Logger l;

private String name;


public Logger getLogger() {
	return l;
}

public String getName() {
	return name;
}

public void sendInfo(String paramString) { 
l.info(paramString);
   }
}