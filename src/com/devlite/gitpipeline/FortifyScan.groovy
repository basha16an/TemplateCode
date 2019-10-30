package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class FortifyScan implements Serializable { 

  def steps;
	def params;
  FortifyScan(steps,params) {
      this.steps = steps
	  this.params=params
  } 
  def fortifyAnalysis(buildEngine){
  for ( int i=0;i<buildEngine.length;i++)
    {
      if ( buildEngine[i].model.id.contains("Auxiliary_Build_Maven")){
	  	steps.echo "Maven Build Model"
		   try{
			  	performFortifyScan(buildEngine[i])
		   } catch(Exception err) {
			  	throw err;
		  }
	  } 
     }
    
  }
  def performFortifyScan(mavenBuildEngine){
  
        def splitgiturl=params.Gitcodeurl.split("/")
	 def repo=splitgiturl[4].tokenize(".")[0]

   def workspace=steps.pwd();
  def fprScanFilename=repo+".fpr"  
  def pdfScanFilename=repo+"_fortify_securityscan_report.pdf"
      if(mavenBuildEngine.buildFile==null || mavenBuildEngine.buildFile==""){
         steps.error "ERROR"
     }
     
     steps.sh '''
     set +x 
     cd ''' + workspace + '''
     export JAVA_HOME='''+ mavenBuildEngine.JavaHome+ '''
     export MAVEN_HOME=/usr/share/maven
     export PATH=$PATH:$MAVEN_HOME/bin:$JAVA_HOME/bin:/opt/Fortify/Fortify_SCA_and_Apps_18.20/bin:/opt/Fortify_home/Fortify/bin
     ${MAVEN_HOME}/bin/mvn com.fortify.sca.plugins.maven:sca-maven-plugin:18.20:clean -f '''+mavenBuildEngine.buildFile+'''
     sourceanalyzer -b '''+mavenBuildEngine.repoName+ ''' mvn package
     sourceanalyzer -b '''+mavenBuildEngine.repoName+ '''  -scan -f '''+fprScanFilename+ '''
     BIRTReportGenerator  -template  "DISA STIG" -source '''+fprScanFilename+ ''' -output '''+ pdfScanFilename+ ''' -format PDF -showSuppressed --Version "DISA STIG 3.9" -UseFortifyPriorityOrder
     
     '''
   }
  
}
