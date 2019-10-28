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
	  
	  steps.echo params.instance_name
	  def file = steps.readFile(workspace +"/pom.xml")
	  
          //def project = new XmlSlurper().parseText(file)
	  def project = new XmlParser().parseText(file.toString()) 
	  def dev_instance_count=params.DevInstances
	  def test_instance_count=params.TestInstances
	  def post_instance_count=params.Prodnstances
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
	  steps.ansibleTower async: false, credential: '', extraVars: 
'''
instance_name: '''+instance_name+'''
artifact_version: '''+artifact_version+'''
dev_instance_count: '''+dev_instance_count+'''
test_instance_count: '''+test_instance_count+'''
prod_instance_count: '''+prod_instance_count+'''
pomgroupId: '''+pomgroupId+'''
pomartifactId: '''+pomartifactId+'''
''', importTowerLogs: true, importWorkflowChildLogs: true, inventory: '', jobTags: '', jobTemplate: 'DEVLITE_Workflow_Cloudbees', jobType: 'run', limit: '', removeColor: true, skipJobTags: '', templateType: 'workflow', throwExceptionWhenFail: true, towerServer: 'AnsibleTower', verbose: true
	//step.sh '''echo "https://github.com/wipropoc/helloworld.git" | awk -F "/" '{print $NF}' | awk -F "." '{print $1}'
     }
}
