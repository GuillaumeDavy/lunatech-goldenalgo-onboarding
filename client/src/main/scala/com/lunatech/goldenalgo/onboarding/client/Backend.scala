package com.lunatech.goldenalgo.onboarding.client

import com.lunatech.goldenalgo.onboarding.diode.Recipe
import io.circe.parser._
import org.scalajs.dom.ext.Ajax

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// Some scaffolding to get you started calling the backend
object Backend {

  private val baseUrl = "http://localhost:8080"
  def fetchRecipes(): Future[Seq[Recipe]] = {
    println("Call backend")
    Ajax.get(s"$baseUrl/recipes")
      .map(xhr => decode[Seq[Recipe]](xhr.responseText).fold(
        _ => Seq.empty,
        recipes => recipes
      ))
  }
}
