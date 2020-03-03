package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
import groovy.util.XmlParser;
class BuildDocker implements Serializable { 

  def steps;
	//def docker;
  BuildDocker(steps) {
      this.steps = steps
	  //this.docker=docker
	  
  } 
  def buildDockerImage(buildEngine){
  steps.echo '**********Build and Compile the code Started**********'
    for ( int i=0;i<buildEngine.length;i++)
    {
      if ( buildEngine[i].model.id.contains("Auxiliary_Build_Maven")){
	  	steps.echo "Maven Build Model"
		try{
		 		buildMavenDockerImage(buildEngine[i])
		  } catch(Exception err) {
			  	throw err;
		  }
	  } 
     }
  steps.echo '**********Build and Compile the code Completed**********'
  }
  def buildMavenDockerImage(MavenbuildEngine){
	  
    def workspace=steps.pwd();
	  
    def file = steps.readFile(workspace +"/pom.xml")
    def project = new XmlParser().parseText(file.toString()) 
    def artifact_version=project.version.text()
    def pomartifactId=project.artifactId.text().toString()
    def registry = "devlite"
    def registryCredential = 'dockerhub'
    def repositoryName=registry + "/" + pomartifactId + ":" + artifact_version
    def dockerApacheImage=steps.docker.build '''+repositoryName+'''
    steps.docker.withRegistry( '', registryCredential ) {
                         dockerApacheImage.push()
                         }
    steps.sh ''' docker rmi -f '''+repositoryName+ ''' ''' 
    }
}
 
