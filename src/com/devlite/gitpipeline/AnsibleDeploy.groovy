package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
import groovy.util.XmlParser;
import groovy.util.XmlSlurper;
class AnsibleDeploy implements Serializable { 

  def steps;
  def params;
  AnsibleDeploy(steps,params) {
      this.steps = steps
      this.params = params
  }
	
  
  def deployusingAnsible(buildEngine,dev_instance_ENDURL,test_instance_ENDURL,prod_instance_ENDURL){
  for ( int i=0;i<buildEngine.length;i++)
    {
      if ( buildEngine[i].model.id.contains("Auxiliary_Build_Maven")){
	  	steps.echo "Maven Build Model"
		   try{
			  ( dev_instance_ENDURL,test_instance_ENDURL,prod_instance_ENDURL)=performAnsibleDeployment(buildEngine[i],dev_instance_ENDURL,test_instance_ENDURL,prod_instance_ENDURL)
		   } catch(Exception err) {
			  	throw err;
		  }
	  } 
     }
	  return [dev_instance_ENDURL,test_instance_ENDURL,prod_instance_ENDURL]
    
  }
  
  def performAnsibleDeployment(mavenBuildEngine,dev_instance_ENDURL,test_instance_ENDURL,prod_instance_ENDURL){
     	def workspace=steps.pwd();
  	 if(mavenBuildEngine.buildFile==null || mavenBuildEngine.buildFile==""){
         steps.error "ERROR"
     	}
	  
	  steps.echo params.instance_name
	  def file = steps.readFile(workspace +"/pom.xml")
	  
          //def project = new XmlSlurper().parseText(file)
	  def project = new XmlParser().parseText(file.toString()) 
	  def dev_instance_count=params.DevInstances
	  
	  def test_instance_count=params.TestInstances
	  def prod_instance_count=params.ProdInstances
	  if (dev_instance_count.toString().equals("null")){
	  	dev_instance_count=0
	  }
	   if (test_instance_count.toString().equals("null")){
	  	test_instance_count=0
	  }
	   if (prod_instance_count.toString().equals("null")){
	  	prod_instance_count=0
	  }
	  def instance_name=params.instance_name
          def artifact_version=project.version.text()
	  def pomartifactId=project.artifactId.text().toString()
	  def pomgroupId=project.groupId.text().toString()
	  
	  steps.echo "Maven Pom version: "+artifact_version
	  steps.echo "Maven Group ID: "+pomgroupId
	  steps.echo "Maven Artifact ID: "+pomartifactId
	  def Parameters="""
	  artifact_version: """+ artifact_version + """
	  instance_name: """+ params.instance_name + """
	  dev_instance_count: """ + params.DevInstances + """
	  test_instance_count: """ + params.TestInstances + """
	  prod_instance_count: """ + params.ProdInstances + """
	  pomgroupId: """+ pomgroupId + """
	  pomartifactId: """+pomartifactId + """
	  """
	  steps.echo Parameters 
	 def ansible_output=steps.ansibleTower async: false, credential: '', extraVars: 
'''
instance_name: '''+instance_name+'''
artifact_version: '''+artifact_version+'''
dev_instance_count: '''+dev_instance_count+'''
test_instance_count: '''+test_instance_count+'''
prod_instance_count: '''+prod_instance_count+'''
pomgroupId: '''+pomgroupId+'''
pomartifactId: '''+pomartifactId+'''
''', importTowerLogs: true, importWorkflowChildLogs: true, inventory: '', jobTags: '', jobTemplate: 'DEVLITE_Workflow_Cloudbees', jobType: 'run', limit: '', removeColor: true, skipJobTags: '', templateType: 'workflow', throwExceptionWhenFail: true, towerServer: 'AnsibleTower', verbose: true
	dev_instance_ENDURL=ansible_output.dev_instance_ENDURL
	test_instance_ENDURL=ansible_output.test_instance_ENDURL
	prod_instance_ENDURL=ansible_output.prod_instance_ENDURL
	
	   steps.echo "DEV URL :" + dev_instance_ENDURL
	steps.echo "TEST URL:" + test_instance_ENDURL
	steps.echo "PROD URL:" + prod_instance_ENDURL 
	 
	  return [ dev_instance_ENDURL,test_instance_ENDURL,prod_instance_ENDURL ]
	 
	
	
	  //step.sh '''echo "https://github.com/wipropoc/helloworld.git" | awk -F "/" '{print $NF}' | awk -F "." '{print $1}'
     }
}
