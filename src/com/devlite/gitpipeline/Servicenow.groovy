package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class Servicenow implements Serializable { 

  def steps;
  Servicenow(steps) {
      this.steps = steps
  } 
  def CreateServiceNowTicket(){
  steps.echo '**********Checkout the code from GIT Started**********'

        //  SUBJECT="Hellowrold Job with Build has been Initiated"
        //  build_description=" Hi Team , \n \n" + SUBJECT + " \n Please  find the BUILD URL:  \n Job Name:  JOB_NAME  \n \n Thanks, \n Wipro DevOps Team"
     //    steps.echo ' ' + build_description + '' 
          def request= steps.ChangeRequest assignedTo:'Ansible Integrations',category:'Other',ci:'AS400',impact:'3 - Low',fullDescription: "testing",Description: "testing",priority:'4 - Low',requestedBy:'Ansible Integrations',risk:'Moderate',shortDescription:"testing",state:'New',type:'Standard'
         steps.createChangeRequest changeRequest: request
 
    steps.echo '**********Checkout the code from GIT Completed**********'
  }
  
}
