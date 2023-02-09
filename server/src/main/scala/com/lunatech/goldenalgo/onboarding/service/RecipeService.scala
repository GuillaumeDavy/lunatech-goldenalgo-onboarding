package com.lunatech.goldenalgo.onboarding.service

import com.lunatech.goldenalgo.onboarding.dto.RecipeDTO
import com.lunatech.goldenalgo.onboarding.model.Recipe

class RecipeService {
  val recipeDto = new RecipeDTO

  def getAllRecipes: Seq[Recipe] = recipeDto.getAllRecipes
  def getRecipesMatchingKeyword(keyword: String): Seq[Recipe] = recipeDto.getRecipesMatchingKeyword(keyword)
  def addRecipe(recipe: Recipe): Seq[Recipe] = recipeDto.addRecipe(recipe)
}
