package com.example.https

import org.junit.rules.ExternalResource
import spray.routing.Route
import scala.beans.BeanProperty

class HttpsServerRule(@BeanProperty val port: Int, val route: Route) extends ExternalResource {
  val server = new HttpsServer(route)
  override def before() = server.start(port)
  override def after() = server.stop()
}

object HttpsServerRule {
  def apply(port: Int) = new HttpsServerRule(port, RestService.defaultRoute);
  def apply(port: Int, route: Route) = new HttpsServerRule(port, route);
}