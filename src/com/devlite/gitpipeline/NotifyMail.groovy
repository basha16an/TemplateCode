package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
import groovy.util.XmlParser;
import groovy.util.XmlSlurper;
class AnsibleDeploy implements Serializable { 

  def steps;
  def params;
  def currentBuild;
  AnsibleDeploy(steps,params) {
      this.steps = steps
      this.params = params
      this.currentBuild=currentBuild
  }
  def sendMail(){
  
  //def mailSubject="The job $JOB_NAME with $BUILD_DISPLAY_NAME Succeeded"
  steps.emailext attachLog: true, body: '''Hi Team, 

The Latest Build jo successful.

Thanks,
Basha''', mimeType: 'test/html', replyTo: 'noreply@wdevlitepoc.com', subject: 'test', to: 'shaik.basha35@wipro.com'
  }
  
  
  
  
 }
