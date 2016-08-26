package service

import java.net.URI

import com.cloudera.livy.LivyClientBuilder

import spray.routing.{HttpService, Route}

import com.cloudera.livy.scalaapi._


trait SprayService extends HttpService {

  import KernelDensityInputJsonSupport._
  import CorrelationInputJsonSupport._
  import KernelDensityResponseJsonProtocol._
  import CorrelationResponseJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  val livyScalaClient = new LivyClientBuilder(false).setURI(new URI("http://172.21.0.228:8998")).build().asScalaClient
  SprayServiceEventHelper.uploadJars(livyScalaClient)

  def adRoute : Route =
    path("home") {
      get {
        getFromResource("webapp/html/home.html")
      }
    } ~
    path("kdensity") {
      get {
        getFromResource("webapp/html/kdensity.html")
      } ~
      post {
        entity(as[KernelDensityInput]) { inputObj =>
          val kdensityResult = SprayServiceEventHelper.processKernelDensityJobEvent(inputObj, livyScalaClient)
          complete {
            KernelDensityResponse(kdensityResult(0), kdensityResult(1))
          }
        }
      }
    } ~
    path("kdensity" / "result") {
      get {
        getFromResource("webapp/html/kdensityresult.html")
      } ~
      post {
        complete {
          val response = SprayServiceEventHelper.getKernelDensityJobJobResult
          KernelDensityResponse(response(0), response(1))
        }
      }
    } ~
    path("correlation") {
      get {
        getFromResource("webapp/html/correlation.html")
      } ~
        post {
          entity(as[CorrelationInput]) { inputObj =>
            val result = SprayServiceEventHelper.processCorrelationJobEvent(inputObj, livyScalaClient)
            complete {
              CorrelationResponse(result)
            }
          }
        }
    } ~
    path("correlation" / "result") {
      get {
        getFromResource("webapp/html/correlationresult.html")
      } ~
        post {
          complete {
            val response = SprayServiceEventHelper.getCorrelationJobJobResult()
            CorrelationResponse(response)
          }
        }
    }
}
