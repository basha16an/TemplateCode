package com.devlite.gitpipeline;
import  com.devlite.gitpipeline.*;
class UploadArtifacts implements Serializable{

  def steps;
  def UploadArtifacts(steps)
  {
      this.steps=steps;
  }
  def uploadBuildArtifacts(buildEngine,URL){
  
    for(int i=0;i<buildEngine.length;i++){
      if(buildEngine[i].model.id.contains("Auxiliary_Build_Maven")){
      try {
          uploadMavenBuildArtifacts(buildEngine[i],URL);
          }catch(Exception err){
            throw err
          }
          
      }
    }
  }

  def uploadMavenBuildArtifacts(mavenBuildEngine,URL){
  def workspace=steps.pwd();
    steps.sh  '''
      export JAVA_HOME=''' + mavenBuildEngine.JavaHome+ '''
      export MAVEN_HOME=/usr/share/maven
      export PATH=$PATH:$MAVEN_HOME/bin:$JAVA_HOME/bin
      $MAVEN_HOME/bin/mvn -f ''' + mavenBuildEngine.buildFile+ ''' deploy -DaltDeploymentRepository=central::default::'''+ URL + '''/''' +mavenBuildEngine.repoName+'''
    '''
  
  }
}
