package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class SonarScan implements Serializable { 

  def steps;
  SonarScan(steps) {
      this.steps = steps
  } 
  def sonarAnalysis(buildEngine,URL){ 
	  steps.echo "**********SonarScan Started"**********"
  for ( int i=0;i<buildEngine.length;i++)
    {
      if ( buildEngine[i].model.id.contains("Auxiliary_Build_Maven")){
	  	steps.echo "Maven Build Model"
		   try{
			  	performSonarScan(buildEngine[i],URL)
		   } catch(Exception err) {
			  	throw err;
		  }
	  } 
     }
    steps.echo "**********SonarScan Completed"**********"
  }
  def performSonarScan(mavenBuildEngine,URL){
  
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
     ${MAVEN_HOME}/bin/mvn -f '''+ mavenBuildEngine.buildFile + ''' sonar:sonar -Dsonar.host.url='''+URL+'''
     '''
   }
  
}
