package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
import groovy.util.XmlParser;
import groovy.util.XmlSlurper;
class AnsibleDeploy implements Serializable { 

  def steps;
  AnsibleDeploy(steps) {
      this.steps = steps
  } 
  def deployusingAnsible(buildEngine){
  for ( int i=0;i<buildEngine.length;i++)
    {
      if ( buildEngine[i].model.id.contains("Auxiliary_Build_Maven")){
	  	steps.echo "Maven Build Model"
		   try{
			  	performAnsibleDeployment(buildEngine[i])
		   } catch(Exception err) {
			  	throw err;
		  }
	  } 
     }
    
  }
  def performAnsibleDeployment(mavenBuildEngine){
  
   def workspace=steps.pwd();
   
   if(mavenBuildEngine.buildFile==null || mavenBuildEngine.buildFile==""){
         steps.error "ERROR"
     }
     def projectconfig = new XmlSlurper().parse(new File(workspace+"/"+mavenBuildEngine.buildFile)) ;
   def pomversion = projectconfig.version.toString()
	  println("Pom Version:" + pomversion)
     
   }
  
}
