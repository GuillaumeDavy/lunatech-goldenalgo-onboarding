package com.lunatech.goldenalgo.onboarding.service

import com.lunatech.goldenalgo.onboarding.diode.Recipe
import com.lunatech.goldenalgo.onboarding.repository.RecipeRepository

import scala.concurrent.{ExecutionContext, Future}

class RecipeService()(implicit val ec: ExecutionContext) {
  private val recipeRepository = new RecipeRepository
  def getAllRecipes: Future[IndexedSeq[Recipe]] = recipeRepository.getAllRecipes
  def upsertRecipe(recipe: Recipe): Future[String] = recipeRepository.upsertRecipe(recipe)
  def getRecipesMatchingKeyword(keyword: String): Future[IndexedSeq[Recipe]] = recipeRepository.getRecipesMatchingKeyword(keyword)
}
