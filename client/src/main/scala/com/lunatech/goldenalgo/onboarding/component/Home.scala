package com.lunatech.goldenalgo.onboarding.component

import com.lunatech.goldenalgo.onboarding.AppRouter
import com.lunatech.goldenalgo.onboarding.component.RecipeComponent.{RecipeComponentProps, recipeComponent}
import com.lunatech.goldenalgo.onboarding.diode.{AppCircuit, GetAllRecipesAction, PrintStateAction, Recipe, SearchRecipeAction}
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
  private var searchWord: String = ""

  private val component = ScalaComponent
    .builder[Props]("Home")
    .render_P { props =>
      <.div(
        <.h1("Recipes"),
        <.input.text(^.onChange ==> onTextChange),
        <.input.submit(^.name := "submit", ^.onClick --> Callback(searchWordClick)),
        <.button("Dispatch event", ^.onClick --> Callback(AppCircuit.dispatch(GetAllRecipesAction))),
        <.button("Print state", ^.onClick --> Callback(AppCircuit.dispatch(PrintStateAction))),
        <.div(displayRecipes(props.component.value))
      )
    }
    .build

  private def onTextChange(e: ReactEventFromInput): Callback = {
    println("In textfield = " + e.target.value)
    searchWord = e.target.value
    Callback()
  }

  private def searchWordClick = AppCircuit.dispatch(SearchRecipeAction(searchWord))

  private def displayRecipes(recipes: Seq[Recipe]) =
    if(recipes.isEmpty)
      <.p("No recipe to display")
    else
      recipes.map(recipe => recipeComponent(RecipeComponentProps(recipe))).toTagMod

  def apply(ctl: RouterCtl[AppRouter.Page]): VdomElement = sc(modelProxy => component(Props(ctl, modelProxy)))
}
