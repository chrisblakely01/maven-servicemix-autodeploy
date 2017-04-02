package com.nanthealth.maven.plugins.deployer.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;


/**
 * @author cblakely
 * manages copying/deleting etc of files and directories
 * 
 */
public class FileManager {

	public void CopyJar(String source, String destination) throws IOException
	{
	    Path FROM = Paths.get(source);
	    Path TO = Paths.get(destination);
	    //overwrite existing file, if exists
	    CopyOption[] options = new CopyOption[]{
	      StandardCopyOption.REPLACE_EXISTING,
	      StandardCopyOption.COPY_ATTRIBUTES
	    }; 
        Files.copy(FROM, TO, options);
	}
	
	//TODO need to fix delete dir async issue
	public void deleteDataFolder(String dateFolderLocation) 
	{
        try
        {
            FileUtils.deleteDirectory(dateFolderLocation);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	//builds the fully qualified location string where each module will be deployed to. TODO is there an api for this?
	public String buildLocationToJarPath(String moduleLocation, String repoPrefix)
	{
		//TODO move this
	    String pomSuffix = "\\pom.xml";
        MavenPom pomUtil = new MavenPom(moduleLocation + pomSuffix);
        
		Model temp = pomUtil.getPom();
		
		String groupId = null;
		String artifactId = null;
		String version = null;
		
		//take the artifactId
		artifactId = temp.getArtifactId();
		
		//if parent exists we take the groupId and the version from it 
		if(temp.getParent()!=null)
		{
			groupId = temp.getParent().getGroupId().replace(".", "\\");
			version = temp.getParent().getVersion();
		}
		else
		{
			groupId = temp.getGroupId().replace(".", "\\");
			version = temp.getVersion();
		}

		//TODO Tidy up this
		String result = repoPrefix + "\\" + groupId + "\\" + artifactId  + "\\" + version + "\\" + artifactId + "-" + version + ".jar";
		System.out.println(result);
		
		return result;

	}	
}
