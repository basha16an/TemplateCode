package com.devlite.gitpipeline;
import  com.devlite.gitpipeline.*;
class UploadArtifacts implemets serializable{

  def steps;
  def UploadArtifacts(steps)
  {
      this.steps=steps;
  }
  def uploadBuildArtifacts(buildEngine,URL){
  
    for(i=0;i<buildEngine.length;i++){
      if(buildEngine[i].model.id.conatins("Auxiliary_Build_Maven")){
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
      export MAVEN_HOME=
    '''
  
  }
}
