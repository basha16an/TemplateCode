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
	  def ansible_output=steps.ansibleTower credential: '', extraVars: ''' artifact_version: ${build_artifact_version}
	  instance_name: ${instance_name} ''' , importTowerLogs: true, importWorkflowChildLogs: true, inventory: '', jobTags: '', jobTemplate: 'VMCloudbeesDeploy_HelloWorld', jobType: 'run', limit: '', removeColor: true, skipJobTags: '', templateType: 'workflow', throwExceptionWhenFail: true, towerServer: 'AnsibleTower', verbose: true
            println ansible_output.Application_END_URL
      //      Application_deployed_url=ansible_output.Application_END_URL
      
       
       
	//  def githuburl = "https://github.com/wipropoc/helloworld.git"
          //def urlFields=githuburl.tokenize('/');
	  //println (urlFields(countTokens()-1))
	 // for (String basha: Repo)
	    //      println (basha)
		//  def Reponames=Repo.split('.');
	  //def Reponame=Reponames(0);
	  //println ("Reponame" +Reponame)
	  
	  //step.sh '''echo "https://github.com/wipropoc/helloworld.git" | awk -F "/" '{print $NF}' | awk -F "." '{print $1}'
    // def projectconfig = new XmlSlurper().parse(new File(workspace+"/"+mavenBuildEngine.buildFile)) ;
   //def pomversion = projectconfig.version.toString()
	//
	//  println("Pom Version:" + pomversion)
     
   }
  
}
