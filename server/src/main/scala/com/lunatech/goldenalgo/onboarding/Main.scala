/*
 * Copyright Audi Electronics Venture GmbH 2019
 */

package com.lunatech.goldenalgo.onboarding

import akka.actor.ActorSystem
import com.lunatech.goldenalgo.onboarding.controller.RecipeController

import scala.concurrent.ExecutionContext

object Main {

  def main(args: Array[String]) = {
    implicit val system: ActorSystem  = ActorSystem("main-system")
    implicit val ec: ExecutionContext = system.dispatcher

    val recipeController = new RecipeController

    new WebServer(recipeController)
      .bind()
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
}
