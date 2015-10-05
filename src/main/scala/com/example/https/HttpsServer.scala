package com.example.https

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern._
import spray.can.Http
import spray.can.server.ServerSettings
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout
import spray.routing.Route
import akka.actor.ActorRef

class HttpsServer(val route: Route = RestService.defaultRoute) extends SslConfiguration {
  implicit val system = ActorSystem()
  implicit val timeout: Timeout = 3 seconds 

  val settings = ServerSettings(system).copy(sslEncryption = true)
  val handler = system.actorOf(Props(new RestService(route)), name = "handler")

  def start(port: Int) = Await.ready(
    IO(Http) ? Http.Bind(handler, interface = "localhost", port = port, settings = Some(settings)), 
    timeout.duration)
      
  def stop() = {
    IO(Http) ? Http.Unbind
    system.stop(handler)
  }
}

object HttpsServer extends App {
  val server = new HttpsServer
  server.start(10999)
}