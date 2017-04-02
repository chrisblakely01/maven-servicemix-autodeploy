package com.nanthealth.maven.plugins.deployer.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.codehaus.plexus.util.cli.CommandLineException;

/**
 * @author cblakely
 * 
 * runs commands against the current runtime
 * 
 */
public class CmdClient {

	public void runCommand(String[] command) throws IOException, InterruptedException, CommandLineException
	{

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        readResponse(process);
	}
	
	private void readResponse(Process proc) throws CommandLineException, IOException{
		BufferedReader stdInput = new BufferedReader(new 
				InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new 
				InputStreamReader(proc.getErrorStream()));

		// read the output from the command
		System.out.println("COMMAND RESULT: ");
		String s = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
		}

		// read any errors from the attempted command
		while ((s = stdError.readLine()) != null) {
			throw new CommandLineException("ERROR RUNNING COMMAND: " + s);
		}
	}
}
