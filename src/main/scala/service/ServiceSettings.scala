package service

import akka.actor.{ ExtendedActorSystem, Extension, ExtensionKey }

object ServiceSettings extends ExtensionKey[ServiceSettings]

/**
  * The settings for Spray backend:
  *   - `interface`: the network interface the service gets bound to, e.g. `"localhost"`.
  *   - `port`: the port the service gets bound to, e.g. `8080`.
  */
class ServiceSettings(system: ExtendedActorSystem) extends Extension {

  /**
    * The network interface the Spray service gets bound to, e.g. `"localhost"`.
    */
  val interface: String = system.settings.config getString "service.interface"

  /**
    * The port the Spray service gets bound to, e.g. `8080`.
    */
  val port: Int = system.settings.config getInt "service.port"

}