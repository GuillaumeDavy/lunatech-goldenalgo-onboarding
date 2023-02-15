package com.lunatech.goldenalgo.onboarding.controller

import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.lunatech.goldenalgo.onboarding.model.Recipe
import com.lunatech.goldenalgo.onboarding.service.RecipeService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.syntax._
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success} //For unmarshalling from JSON to Entity

class RecipeController()(implicit val ec: ExecutionContext) {
  private val logger: Logger = LoggerFactory.getLogger(classOf[RecipeController])
  private val recipeService = new RecipeService
  private def myExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case ex: Exception =>
        extractUri { uri =>
          println(s"Request to $uri could not be handled normally")
          complete(InternalServerError, s"An error occurred: ${ex.getMessage}")
        }
    }

  val recipes: Route =
    handleExceptions(myExceptionHandler) {
      concat(
        pathPrefix("recipes") {
          concat(
            //        parameter("search".as[String]) {
            //          keyword =>
            //            logger.info(s"GET recipes with attributes keyword=${keyword}")
            //            respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
            //              recipeService.getRecipesMatchingKeyword(keyword) match {
            //                case Nil => complete(HttpResponse(StatusCodes.BadRequest, entity=s"No recipe found for keyword=${keyword}"))
            //                case myList @ _ => complete(HttpEntity(ContentTypes.`application/json`, myList.asJson.noSpaces))
            //              }
            //            }
            //        },
            get {
              logger.info("GET all recipe called")
              respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
                onSuccess(recipeService.getAllRecipes)(recipes => complete(HttpEntity(ContentTypes.`application/json`, recipes.asJson.noSpaces)))
              }
            },
            post {
              entity(as[Recipe]) { recipe: Recipe =>
                logger.info(s"Post recipe=${recipe}")
                respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
                  onSuccess(recipeService.upsertRecipe(recipe))(recipeId => complete(HttpEntity(ContentTypes.`application/json`, recipeId.asJson.noSpaces)))
                }
              }
            }
          )
        }
      )
    }

  val routes: Route = recipes
}
