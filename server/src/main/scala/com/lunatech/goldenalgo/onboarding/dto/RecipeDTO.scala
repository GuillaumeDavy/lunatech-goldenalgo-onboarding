package com.lunatech.goldenalgo.onboarding.dto

import com.lunatech.goldenalgo.onboarding.model.Recipe

class RecipeDTO {
  //TODO : link to elasticSearch

  var recipes: Seq[Recipe] = Seq(
    Recipe("recipe1-id", "recipe1-name", Seq("ingredient1", "ingredient2"), Seq("instruction1", "instruction2")),
    Recipe("recipe2-id", "recipe2-name", Seq("ingredient1", "ingredient2"), Seq("instruction1", "instruction2")),
    Recipe("recipe3-id", "recipe3-name", Seq("ingredient1", "ingredient2"), Seq("instruction1", "instruction2")),
    Recipe("recipe4-id", "recipe4-name", Seq("ingredient1", "ingredient2"), Seq("instruction1", "instruction2")),
    Recipe("recipe5-id", "recipe5-name", Seq("ingredient1", "ingredient2"), Seq("instruction1", "instruction2"))
  )

  def getAllRecipes: Seq[Recipe] = recipes
  def getRecipesMatchingKeyword(keyword: String): Seq[Recipe] = recipes.filter(recipe => recipe.id.equals(keyword) || recipe.name.equals(keyword) || recipe.ingredients.contains(keyword) || recipe.instructions.contains(keyword))
  def addRecipe(recipe: Recipe): Seq[Recipe] = {
    recipes = recipes :+ recipe
    recipes
  }
}