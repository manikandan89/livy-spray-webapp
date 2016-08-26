package service

import spray.json.DefaultJsonProtocol

case class CorrelationResponse(result: Double)
case class KernelDensityResponse(estimationPoints: Array[Double], results: Array[Double])

object KernelDensityResponseJsonProtocol extends DefaultJsonProtocol {
  implicit val kDensityResponseMessage = jsonFormat2(KernelDensityResponse)
}

object CorrelationResponseJsonProtocol extends DefaultJsonProtocol {
  implicit val correlationResponseMessage = jsonFormat1(CorrelationResponse)
}


