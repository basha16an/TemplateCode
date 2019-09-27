package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class BuildEngine implements Serializable { 

  def steps;
  BuildEngine(steps) {
      this.steps = steps
  } 
  def BuildExecute(buildEngine){
  
    for ( int i=0;i<buildEngine.length;i++)
    {
      if ( buildEngine[i].model.id.contains("Auxiliary_Build_Maven")){
	  	steps.echo "Maven Build Model"
		try{
		 		def buildObj = new com.devlite.gitpipeline.MavenBuild(steps)
			  	buildObj.buildAppByMaven(buildEngine[i])
		  } catch(Exception err) {
			  	throw err;
		  }
	  } 
     }
  }
  
}
