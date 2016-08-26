package jobs

import com.cloudera.livy.scalaapi.LivyScalaClient
import org.apache.spark.mllib.stat.KernelDensity

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Sorting

class KernelDensityJob(livyScalaClient: LivyScalaClient) {

  var result: Array[Array[Double]] = null

  def setResult(result: Array[Array[Double]]) = this.result = result

  def getResult = this.result

  def process(column: String, points: Array[Double]): Array[Double] = {
    val handle = livyScalaClient.submit { context =>
      val sc = context.sc
      val sqlctx = context.sqlctx
      val df = sqlctx.read.format("com.databricks.spark.csv")
        .option("header", "true").option("inferSchema", "true")
        .load("/Users/manikandan.nagarajan/Desktop/datasets/demo/forestfires.csv")
      df.printSchema()
      df.show()
      val some = df.rdd.map(row => row.getDouble(row.fieldIndex(column)))
      val data = some.collect()
      Sorting.quickSort(data)

      val sortedRDD = sc.parallelize(data)
      val count = sortedRDD.count
      val mean = sortedRDD.sum / count
      val devs = sortedRDD.map(score => (score - mean) * (score - mean))
      val stddev = Math.sqrt(devs.sum / count)
      val dn = scala.math.pow(count, 0.2)
      val bandwidth = (1.06 * stddev) / dn
      val kernelDensity =  new KernelDensity().setSample(sortedRDD).setBandwidth(bandwidth)
      val densities = kernelDensity.estimate(points)
      densities
    }
    Await.result(handle, 120 second)
  }
}
