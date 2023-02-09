package com.lunatech.goldenalgo.onboarding

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http.{ServerBinding, lookup}
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn
import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.Http
import com.lunatech.goldenalgo.onboarding.controller.RecipeController

class WebServer(controller: RecipeController)(implicit val system: ActorSystem, executor: ExecutionContext){
  def bind(): Future[ServerBinding] = {
    val routes = controller.routes

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return

    bindingFuture
  }
}
