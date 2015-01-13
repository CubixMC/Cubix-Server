package org.cubixmc.server;

import java.util.logging.Logger;


public class Logger {

private Logger l;

private String name;


public Logger getLogger(){
	return l;
}
public String getName(){
	return name;
}
public void info(String paramString){
	System.out.println(getName() + " " + paramString);
}


}