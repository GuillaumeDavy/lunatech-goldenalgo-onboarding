package com.lunatech.goldenalgo.onboarding.component

import com.lunatech.goldenalgo.onboarding.AppRouter
import com.lunatech.goldenalgo.onboarding.component.HeaderComponent.headerComponent
import com.lunatech.goldenalgo.onboarding.component.RecipeComponent.{RecipeComponentProps, recipeComponent}
import com.lunatech.goldenalgo.onboarding.diode.{AppCircuit, Recipe}
import diode.react.{ModelProxy, ReactConnectProxy}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.html_<^._

object Home {

  case class Props(
      ctl: RouterCtl[AppRouter.Page],
      component: ModelProxy[Seq[Recipe]],
  )

  private val sc: ReactConnectProxy[Seq[Recipe]] = AppCircuit.connect(recipes => recipes)

  private val component = ScalaComponent
    .builder[Props]("Home")
    .render_P { props =>
      <.div(
        headerComponent(),
        <.div(
          ^.margin := "10px",
          displayRecipes(props.component.value)
        )
      )
    }
    .build

  private def displayRecipes(recipes: Seq[Recipe]): TagMod =
    if(recipes.isEmpty)
      <.p("No recipe to display")
    else {
      <.div(
        recipes.map(recipe => recipeComponent(RecipeComponentProps(recipe))).toTagMod
      )
    }

  def apply(ctl: RouterCtl[AppRouter.Page]): VdomElement = sc(modelProxy => component(Props(ctl, modelProxy)))
}
