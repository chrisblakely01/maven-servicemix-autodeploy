package com.nanthealth.maven.plugins.deployer.core;

import java.util.Arrays;
import java.util.List;

public class MavenConstants {
	
	//Maven invoker
	protected static final List<String> MAVEN_INVOKER_CLEAN_INSTALL_GOALS = Arrays.asList("clean", "install", "-e" );
	
	//Maven Client
	protected static int MAVEN_CLIENT_RESULT_SUCCESS = 0;
	

}
