package com.lunatech.goldenalgo.onboarding

import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport

object App {

  @JSExport
  def main(args: Array[String]): Unit = {

    val app = dom.document.getElementById("app")
    AppRouter.router().renderIntoDOM(app)
  }
}
