package com.nanthealth.maven.plugins.deployer.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;


//Utiilty class used to get data from a maven pom file
public class MavenPom
{
    private String pomLocation;
    
    public MavenPom(String pomLocation)
    {
        this.pomLocation = pomLocation;
    }
    
    //get pom object
    public Model getPom()
    {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        
        Model model = null;
        
        try {
            model = reader.read(new FileReader(pomLocation));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            
        } catch (IOException e) {
            e.printStackTrace();
            
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return model;
    }
    
    //get pom properties
    public Properties getProperties()
    {
        return getPom().getProperties ();
    }
    
    //get pom properties
    public List <String> getModules()
    {
        return getPom().getModules();
    }
    
}
