package service

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http



object Boot extends App {

  private def waitForExit() = {
    def waitEOF(): Unit = Console.readLine() match {
      case "exit" => system.shutdown()
      case _ => waitEOF()
    }
    waitEOF()
  }

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("actor-system")

  // create and start our service actor
  val service = system.actorOf(Props[ServiceActor], "service-actor")

  // start a new HTTP server on port 8080 with our service actor as the handler
  val interface = ServiceSettings(system).interface
  val port = ServiceSettings(system).port
  IO(Http) ! Http.Bind(service, interface, port)

  Console.println(s"Server started ${system.name}, $interface:$port")
  Console.println("Type `exit` to exit....")

  waitForExit()
  system.shutdown()

}