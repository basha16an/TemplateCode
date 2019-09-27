package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class MavenBuild implements Serializable { 

  def steps;
  MavenBuild(steps) {
      this.steps = steps
  } 
  def buildAppByMaven(mavenBuildEngine){
  
   def workspace=steps.pwd();
  
      if(mavenBuildEngine.buildFile==null || mavenBuildEngine.buildFile==""){
         steps.error "ERROR"
     }
     steps.sh '''
     set +x 
     cd ''' + workspace + '''
     export JAVA_HOME='''+ mavenBuildEngine.JavaHome+ '''
     export MAVEN_HOME=/usr/share/maven
     export PATH=$PATH:$MAVEN_HOME/bin:$JAVA_HOM/bin
     ${MAVEN_HOME}/bin/mvn -f '''+ mavenBuildEngine.buildFile + ''' '''+ mavenBuildEngine.buildTarget
     '''
   }
  
}
