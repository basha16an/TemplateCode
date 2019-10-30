package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class CheckOut implements Serializable { 

  def steps;
  CheckOut(steps) {
      this.steps = steps
  } 
  def CheckoutModules(scmEngine){
  steps.echo "**********Checkout the code from GIT Started"**********"
    for ( int i=0;i<scmEngine.length;i++)
    {
      steps.checkout scmEngine[i]
     }
    steps.echo "**********Checkout the code from GIT Completed"**********"
  }
  
}
