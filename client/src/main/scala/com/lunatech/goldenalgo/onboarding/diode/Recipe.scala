package com.lunatech.goldenalgo.onboarding.diode

import com.lunatech.goldenalgo.onboarding.client.Backend
import diode.ActionResult.{ModelUpdate, ModelUpdateEffect}
import diode.react.ReactConnector
import diode.{Action, Circuit, Effect, ModelRO}
import io.circe._
import io.circe.generic.semiauto._

import scala.concurrent.ExecutionContext.Implicits.global

//MODEL
case class Recipe(id: String, name: String, ingredients: Seq[String], instructions: Seq[String])

object Recipe {
  implicit val codec: Codec[Recipe] = deriveCodec[Recipe]
}


//CIRCUIT
object AppCircuit extends Circuit[Seq[Recipe]] with ReactConnector[Seq[Recipe]]{
  override protected def initialModel: Seq[Recipe] = Seq.empty
  override protected def actionHandler: AppCircuit.HandlerFunction =
    (model, action) => action match {
      case GetAllRecipesAction =>
        println("GetAllRecipesAction called")
        Some(ModelUpdateEffect(
          model,
          Effect(Backend.fetchRecipes().map(recipes => ModifyStateAction(recipes))) // Effect that trigger ModifyStateAction
        ))
      case ModifyStateAction(recipes) =>
        println("ModifyStateAction called")
        Some(ModelUpdate(recipes))
      case ResetAction =>
        println("ResetAction called")
        Some(ModelUpdate(Seq.empty))
      case PrintStateAction =>
        println(model)
        Some(ModelUpdate(model))
      case _ => None
    }

  val reader: ModelRO[Seq[Recipe]] = AppCircuit.zoom(recipes => recipes)
}

// ACTIONS
case object GetAllRecipesAction extends Action
case class ModifyStateAction(recipes: Seq[Recipe]) extends Action
case object ResetAction extends Action
case object PrintStateAction extends Action
