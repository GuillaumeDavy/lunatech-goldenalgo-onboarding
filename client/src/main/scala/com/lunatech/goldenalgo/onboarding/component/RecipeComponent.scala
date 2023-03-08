package com.lunatech.goldenalgo.onboarding.component

import com.lunatech.goldenalgo.onboarding.diode.Recipe
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._

object RecipeComponent {

  case class RecipeComponentProps(recipe: Recipe)

  val recipeComponent = ScalaComponent.builder[RecipeComponentProps]
    .render_P { props =>
      <.div(
        ^.margin := "10px",
        ^.padding := "10px",
        ^.border := "1px solid black",
        ^.borderRadius := "5px",
        <.h1(props.recipe.name),
        <.h3("Ingredients"),
        <.div(displayElements(props.recipe.ingredients)),
        <.h3("Instructions"),
        <.div(displayElements(props.recipe.instructions))
      )
    }
    .build

  private def displayElements(elements: Seq[String]) = {
    if (elements.isEmpty) {
      <.p("No element to display")
    } else {
      <.ol
        elements.map(element => <.li(element)).toTagMod
    }
  }
}