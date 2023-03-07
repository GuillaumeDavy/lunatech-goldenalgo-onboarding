package com.lunatech.goldenalgo.onboarding.component

import com.lunatech.goldenalgo.onboarding.diode.{AppCircuit, GetAllRecipesAction, SearchRecipeAction}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, ReactEventFromInput, ScalaComponent}

object HeaderComponent {

  val headerComponent = ScalaComponent.builder[Unit]
    .render_P {_ =>
      <.div(
        <.h1("Recipes"),
        <.input.text(
          ^.width := "50%",
          ^.placeholder := "Search ingredient",
          ^.onChange ==> onTextChange
        ),
      )
    }
    .build

  private def onTextChange(e: ReactEventFromInput): Callback = {
    val value = e.target.value
    if (value.isEmpty)
      Callback(AppCircuit.dispatch(GetAllRecipesAction))
    else
      Callback(AppCircuit.dispatch(SearchRecipeAction(value)))
  }
}