package service

import akka.actor.Actor
import spray.routing._

/**
  * Actor for HttpServices.
  *
  * Extend this actor with Spray HttpService traits to add http services.
  * - adroute from SprayService
  * - staticRoute to static files under webapp directory
  */
class ServiceActor
  extends Actor
    with SprayService
{

  def actorRefFactory = context

  def receive = runRoute(adRoute ~ staticRoute)

  def staticRoute: Route =
    path("")(getFromResource("webapp/index.html")) ~ getFromResourceDirectory("webapp")

}