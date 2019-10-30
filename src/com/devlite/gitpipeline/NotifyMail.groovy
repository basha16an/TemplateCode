package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
import groovy.util.XmlParser;
import groovy.util.XmlSlurper;
class NotifyMail implements Serializable { 

  def steps;
  def params;
  def currentBuild;
  NotifyMail(steps,params) {
      this.steps = steps
      this.params = params
  }
  def sendMail(BuildStatus,dev_instance_ENDURL,test_instance_ENDURL,prod_instance_ENDURL){
	  
	  def MAILID=params.MAILIDs
	  /*
	  steps.echo params.Gitcodeurl 
  def splitgiturl=params.Gitcodeurl.split("/")
	  steps.echo splitgiturl.toString();
 // def repo=splitgiturl[4].split("\.")
	  def repo=splitgiturl[4].tokenize(".")[1]
	  steps.echo repo.toString()
  def gitreponame=repo[1]
 steps.echo gitreponame */
   steps.emailext subject: 'The $JOB_BASE_NAME job $BUILD_DISPLAY_NAME Build Status is ' + BuildStatus , 
   body: '''
    Hi All,
  
        The Current Build $BUILD_DISPLAY_NAME is '''+ BuildStatus + '''
	BUILD URL= $BUILD_URL
        DEV URL  = ''' +  dev_instance_ENDURL + '''
	TEST URL = ''' + test_instance_ENDURL + '''
	PROD URL = ''' + prod_instance_ENDURL + '''
	Please Find  the attached Build Log
        Please find the input parameter values:
        --------------
	      instance_name   = '''+params.instance_name+'''
	      Gitcodeurl      = '''+params.Gitcodeurl+'''
	      GitBranch       = '''+params.GitBranch+'''
	      Language        = '''+params.Language+'''
	      Languageversion = '''+params.Languageversion+'''	
	      ServerType      = '''+params.ServerType+'''
	      FieldType	      = '''+params.FieldType+'''
	      DevInstances    = '''+params.DevInstances+'''
	      DTshirtsize     = '''+params.DTshirtsize+'''
	      TestInstances   = '''+params.TestInstances+'''
	      TTshirtsize     = '''+params.TTshirtsize+'''
	      ProdInstances   = '''+params.ProdInstances+'''	
	      MAILIDs	      = '''+params.MAILIDs+'''
  Thanks,
  Devops Team ''', replyTo: 'no-reply@wipro-poc.com',
                        from:'no-reply@wipro-poc.com',
                        to: MAILID ,
                        attachLog:'true',
                        attachmentsPattern:'*.pdf'
  } 
 }
