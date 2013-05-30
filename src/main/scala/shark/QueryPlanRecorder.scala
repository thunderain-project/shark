package shark

import scala.collection.mutable.Queue
import java.io.PrintStream
import shark.execution.ExplainTaskHelper
import java.io.ByteArrayOutputStream

//it is used to record query plan graph
object QueryPlanRecorder {
  var planQue=new Queue[(PrintStream,ByteArrayOutputStream)]//store information in Memory 
  
//generate query plan graph for a given operator and record in ByteArrayOutputStream
  def recordQueryPlan(terminalOp:Serializable) : Unit={
    val querystream = new ByteArrayOutputStream
    val out = new PrintStream(querystream)
    ExplainTaskHelper.outputPlan(terminalOp, out, true, 2)
//    out.close
    planQue.enqueue((out,querystream))
  }
 
//add information to query plan graph
  def addInfo2QueryPlan(info:String) : Unit={
    planQue.head._1.println(info)// the query plans are executed by FIFO
  }

//transfer query plan graph 
  def getQueryPlan() : String={
    if(planQue.size > 0){
      planQue.head._1.close
      planQue.dequeue._2.toString
    }else{
      "no query plan related!"
    }
  }
}
