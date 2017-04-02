package com.nanthealth.maven.plugins.deployer.core;

import java.io.File;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class MavenInvoker {

	public void buildAggregator(String module) throws MavenInvocationException
	{
		runCleanInstall(module);	
	}

	private void runCleanInstall(String moduleLocation) throws MavenInvocationException
	{
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile( new File( moduleLocation) );
		request.setGoals( MavenConstants.MAVEN_INVOKER_CLEAN_INSTALL_GOALS );

		Invoker invoker = new DefaultInvoker();
		InvocationResult result = invoker.execute( request );
		checkExecutionSuccess (result);

	}

    private void checkExecutionSuccess (InvocationResult result)
    {
        if ( result.getExitCode() != MavenConstants.MAVEN_CLIENT_RESULT_SUCCESS )
		{
		    throw new IllegalStateException( "Build failed." );
		}
    }

}
