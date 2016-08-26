package jobs


import com.cloudera.livy.scalaapi.LivyScalaClient
import org.apache.spark.mllib.stat.Statistics

import scala.concurrent.Await
import scala.concurrent.duration._


class CorrelationJob(livyScalaClient: LivyScalaClient) {

  var result: Double = 0

  def setResult(result: Double) = this.result = result

  def getResult = this.result

  def process(column1: String, column2: String, method: String): Double = {
    val handle = livyScalaClient.submit { context =>
      val sc = context.sc
      val sqlctx = context.sqlctx
      val df = sqlctx.read.format("com.databricks.spark.csv")
        .option("header", "true").option("inferSchema", "true")
        .load("/Users/manikandan.nagarajan/Desktop/datasets/demo/forestfires.csv")
      df.printSchema()
      df.show()
      val column1RDD = df.rdd.map(row => row.getDouble(row.fieldIndex(column1)))
      val column2RDD = df.rdd.map(row => row.getDouble(row.fieldIndex(column2)))
      Statistics.corr(column1RDD, column2RDD, method)
    }
    Await.result(handle, 120 second)
  }
}
