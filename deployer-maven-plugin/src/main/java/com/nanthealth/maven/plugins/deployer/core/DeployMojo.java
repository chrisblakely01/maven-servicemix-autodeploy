package com.nanthealth.maven.plugins.deployer.core;

import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.codehaus.plexus.util.cli.CommandLineException;

/**
 * @author cblakely
 * maven plugin to deploy modules to hsip
 *
 */

@Mojo( name = "deploy")
public class DeployMojo extends AbstractMojo
{
    @Parameter( defaultValue = "${project}", required=true)
    private MavenProject callingProject;   

    @Parameter(defaultValue = "${basedir}")
    private String callingProjectBaseDir;   
    
    @Parameter(defaultValue = "${settings.localRepository}", required=true)
    private String localMavenRepoBaseDir;
    
    @Parameter(required=true)
    private String servicemixBaseDir;
    
    @Parameter (defaultValue = "${servicemixBaseDir}\\data")
    private String servicemixDataDir;
    
    @Parameter  (defaultValue = "${servicemixBaseDir}\\system")
    private String servicemixRepoBaseDir;

    private FileManager fileManager = new FileManager();
    private MavenInvoker mavenInvoker = new MavenInvoker();
    private CmdClient client = new CmdClient();

	public void execute()
	{ 
	    try
        {
            executeCleanInstallAndDeploy();
        }
        catch (MavenInvocationException | IOException | InterruptedException | CommandLineException e)
        {
            printExceptionDetails(e);
        }
	}

    private void executeCleanInstallAndDeploy() throws MavenInvocationException, IOException, InterruptedException, CommandLineException
    {
        String aggregatorPomLocation = callingProject.getBasedir() + "\\pom.xml";
	    List<String> modules = callingProject.getModules();

		buildAggregatorProject (aggregatorPomLocation);	
		
		stopHsipProcess();
		
		fileManager.deleteDataFolder(servicemixDataDir);

		copyModuleJarsFromSourceToDestination (modules);
		
		startHsipProcess ();
    }


    private void buildAggregatorProject (String aggregatorPomLocation) throws MavenInvocationException
    {
        mavenInvoker.buildAggregator(aggregatorPomLocation);

    }
    
    private void stopHsipProcess () throws IOException, InterruptedException, CommandLineException
    {
        client.runCommand(CmdConstants.STOP_HSIP);
    }

    private void copyModuleJarsFromSourceToDestination (List <String> modules) throws IOException
    {
        for(String module : modules)
		{
			String sourcePath = fileManager.buildLocationToJarPath(module, localMavenRepoBaseDir);
			String destinationPath = fileManager.buildLocationToJarPath(module, servicemixRepoBaseDir);

			fileManager.CopyJar(sourcePath, destinationPath);
		}
    }
    

    private void startHsipProcess() throws IOException, InterruptedException, CommandLineException
    {
        client.runCommand(CmdConstants.START_HSIP);
    }

    private void printExceptionDetails (Exception e)
    {
        System.out.println("========================");
        System.out.println("STACK TRACE: ");
        e.printStackTrace();
        System.out.println("========================");
        System.out.println("MESSAGE: " + e.getMessage());
        System.out.println("========================");
        System.out.println("ERROR PERFORMING DEPLOY: See errors for details");
    }

}
