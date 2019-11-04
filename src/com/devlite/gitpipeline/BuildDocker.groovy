package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class BuildDocker implements Serializable { 

  def steps;
  BuildDocker(steps) {
      this.steps = steps
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
  def buildMavenDockerImage(buildEngine){
	  def workspace=steps.pwd();
    def file = steps.readFile(workspace +"/pom.xml")
    def artifact_version=project.version.text()
	  def project = new XmlParser().parseText(file.toString()) 
	  def pomgroupId=project.groupId.text().toString()
    def registry = "devlite"
    def registryCredential = 'dockerhub'
    def repositoryName=reistry+"/"+pomgroupId+":"+artifact_version
    dockerApacheImage=steps.docker.build repositoryName
    steps.docker.withRegistry( '', registryCredential ) {
                         steps.dockerApacheImage.push()
                         }
    steps.sh ''' docker rmi -f '''+repositoryName+ ''' '''
    }
}
