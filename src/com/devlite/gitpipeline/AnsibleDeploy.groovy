package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
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
     
     steps.sh '''
     set +x 
     cd ''' + workspace + '''
     export JAVA_HOME='''+ mavenBuildEngine.JavaHome+ '''
     export MAVEN_HOME=/usr/share/maven
     export PATH=$PATH:$MAVEN_HOME/bin:$JAVA_HOME/bin:/opt/Fortify/Fortify_SCA_and_Apps_18.20/bin
     ${MAVEN_HOME}/bin/mvn com.fortify.sca.plugins.maven:sca-maven-plugin:18.20:clean -f '''+mavenBuildEngine.buildFile+'''
     sourceanalyzer -b '''+mavenBuildEngine.repoName+ ''' mvn package
     sourceanalyzer -b '''+mavenBuildEngine.repoName+ '''  -scan -f '''+fprScanFilename+ '''
     BIRTReportGenerator  -template  "DISA STIG" -source '''+fprScanFilename+ ''' -output '''+ pdfScanFilename+ ''' -format PDF -showSuppressed --Version "DISA STIG 3.9" -UseFortifyPriorityOrder
     
     '''
   }
  
}
