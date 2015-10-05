package com.example.https

import akka.pattern.ask
import akka.actor._
import spray.can.Http
import spray.http._
import spray.routing.HttpServiceActor

import spray.routing._
import Directives._

class RestService(val route: Route) extends HttpServiceActor with ActorLogging {
  def receive = runRoute {
    route
  }
}

object RestService {
  val defaultRoute = path("") {
    get {
      complete {
        "OK!\n"
      }
    }
  }
}
