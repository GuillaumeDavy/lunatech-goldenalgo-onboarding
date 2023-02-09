package com.lunatech.goldenalgo.onboarding.controller

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.lunatech.goldenalgo.onboarding.model.Recipe
import com.lunatech.goldenalgo.onboarding.service.RecipeService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.syntax._

import scala.concurrent.ExecutionContext //For unmarshalling from JSON to Entity

class RecipeController()(implicit val ec: ExecutionContext) {
  private val recipeService = new RecipeService

  val recipes: Route = concat(
    pathPrefix("recipes") {
      concat(
        parameter("search".as[String]) {
          keyword =>
            respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
              recipeService.getRecipesMatchingKeyword(keyword) match {
                case Nil => complete(HttpResponse(StatusCodes.BadRequest, entity=s"No recipe found for keyword=${keyword}"))
                case myList @ _ => complete(HttpEntity(ContentTypes.`application/json`, myList.asJson.noSpaces))
              }
            }
        },
        get {
          respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
            complete(HttpEntity(ContentTypes.`application/json`, recipeService.getAllRecipes.asJson.noSpaces))
          }
        },
        post {
          entity(as[Recipe]) { recipe: Recipe =>
            respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
              complete(HttpEntity(ContentTypes.`application/json`, recipeService.addRecipe(recipe).asJson.noSpaces))
            }
          }
        }
      )
    }
  )
  val routes: Route = recipes
}
