package service

import java.net.URI

import com.cloudera.livy.scalaapi.LivyScalaClient
import jobs.{CorrelationJob, KernelDensityJob}

import scala.concurrent.Await
import scala.concurrent.duration._


object SprayServiceEventHelper {

  var kDensityJob: KernelDensityJob = null
  var correlationJob: CorrelationJob = null

  def processKernelDensityJobEvent(kernelDensityInput: KernelDensityInput, client: LivyScalaClient): Array[Array[Double]] = {
    kDensityJob = new KernelDensityJob(client)
    val column = kernelDensityInput.column
    val pointsArray = getPointsArray(kernelDensityInput.points)
    val output = kDensityJob.process(column, pointsArray)
    val roundedOutput = output.map(value => (BigDecimal(value * 100).setScale(3, BigDecimal.RoundingMode.HALF_UP).toDouble))
    val response = Array(pointsArray, roundedOutput)
    kDensityJob.setResult(response)
    response
  }

  private def getPointsArray(pointString: String): Array[Double] = {
    val pointsStringArray = pointString.split(",").map { point =>
      point.toDouble
    }
    pointsStringArray
  }

  def getKernelDensityJobJobResult(): Array[Array[Double]] = {
    kDensityJob.getResult
  }

  def processCorrelationJobEvent(correlationInput: CorrelationInput, client: LivyScalaClient): Double = {
    correlationJob = new CorrelationJob(client)
    val column1 = correlationInput.column1
    val column2 = correlationInput.column2
    val method = correlationInput.method
    val response = correlationJob.process(column1, column2, method)
    correlationJob.setResult(response)
    response
  }

  def getCorrelationJobJobResult(): Double = {
    correlationJob.getResult
  }

  def uploadJars(livyScalaClient: LivyScalaClient): Unit = {
    Thread.sleep(8000)
    val handle1 = livyScalaClient.addJar(new URI("/Users/manikandan.nagarajan/jars/livy-spray-webapp_2.10-1.0.jar"))
    Await.result(handle1, 30 second) match {
      case null => println("Uploaded app file")
    }
    val handle2 = livyScalaClient.addJar(new URI("/Users/manikandan.nagarajan/jars/livy-scala-api-0.3.0-SNAPSHOT.jar"))
    Await.result(handle2, 30 second) match {
      case null => println("Uploaded scala api file")
    }
    val handle3 = livyScalaClient.addJar(new URI("/Users/manikandan.nagarajan/jars/spark-csv_2.10-1.4.0.jar"))
    Await.result(handle3, 30 second) match {
      case null => println("Uploaded spark csv file")
    }
    val handle4 = livyScalaClient.addJar(new URI("/Users/manikandan.nagarajan/jars/commons-csv-1.4.jar"))
    Await.result(handle4, 30 second) match {
      case null => println("Uploaded common csv file")
    }
  }
}
