package com.devlite.gitpipeline;
import com.devlite.gitpipeline.*;
class CheckOut implements Serializable { 

  def steps;
  CheckOut(steps) {
      this.steps = steps
  } 
  def CheckoutModules(scmEngine){
  
    for ( int i=0;i<scmEngine.length;i++)
    {
      steps.checkout Git[i]
     }
  }
  
}
