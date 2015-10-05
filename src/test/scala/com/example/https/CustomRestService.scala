package com.example.https

import spray.routing._
import Directives._

object CustomRestService {
  val route = 
    path("api" / "user" / IntNumber) { id =>
      get {
        complete {
          "a@b.com"
        }
      }
    }
}