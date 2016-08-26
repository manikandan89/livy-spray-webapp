package service

import spray.json.DefaultJsonProtocol

case class KernelDensityInput(column: String, points: String)

case class CorrelationInput(column1: String, column2: String, method: String)

object KernelDensityInputJsonSupport extends DefaultJsonProtocol {
  implicit val kdensityFormat = jsonFormat2(KernelDensityInput)
}

object CorrelationInputJsonSupport extends DefaultJsonProtocol {
  implicit val correlationFormat = jsonFormat3(CorrelationInput)
}



