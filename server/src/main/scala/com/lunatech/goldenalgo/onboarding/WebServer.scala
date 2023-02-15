package com.lunatech.goldenalgo.onboarding

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import com.lunatech.goldenalgo.onboarding.controller.RecipeController

import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn

class WebServer(controller: RecipeController)(implicit val system: ActorSystem, executor: ExecutionContext){
  def bind(): Future[ServerBinding] = {
    val routes = controller.routes

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return

    bindingFuture
  }
}
