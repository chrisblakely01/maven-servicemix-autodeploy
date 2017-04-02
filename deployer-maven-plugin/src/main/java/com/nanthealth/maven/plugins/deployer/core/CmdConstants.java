package com.nanthealth.maven.plugins.deployer.core;

/**
 * @author cblakely
 * list of available commands passed to the CmdClient
 * 
 */
public class CmdConstants {
	
    public static final String[] START_HSIP = {"cmd.exe", "/c", "sc", "start", "hsip"};
    public static final String[] STOP_HSIP = {"cmd.exe", "/c", "sc", "stop", "hsip"};
    public static final String[] IS_RUNNING_HSIP = {"cmd.exe", "/c", "sc", "query", "hsip", "|", "find", "/C", "\"RUNNING\""};
}
