package com.lunatech.goldenalgo.onboarding.repository

import com.lunatech.goldenalgo.onboarding.model.MyImplicit.{RecipeHitReader, RecipeIndexable}
import com.lunatech.goldenalgo.onboarding.model.Recipe
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s._
import com.sksamuel.elastic4s.http.JavaClient
import com.sksamuel.elastic4s.requests.searches.SearchResponse

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class RecipeRepository()(implicit val ec: ExecutionContext) {
  //Connect to elasticSearch
  private val elasticClient: ElasticClient = ElasticClient(JavaClient(ElasticProperties("http://localhost:9200")))
  //Create index
  elasticClient.execute(createIndex("recipes"))

  def getAllRecipes: Future[IndexedSeq[Recipe]] = elasticClient
    .execute(search("recipes"))
    .map(response => response.result.to[Recipe])


  def upsertRecipe(recipe: Recipe): Future[String] = elasticClient
    .execute(
      indexInto("recipes")
        .id(recipe.id)
        .doc(recipe)
    ).map(response => response.result.id)
  //TODO: elasticSearch requests
//  def getRecipesMatchingKeyword(keyword: String): Seq[Recipe] = recipes.filter(recipe => recipe.id.equals(keyword) || recipe.name.equals(keyword) || recipe.ingredients.contains(keyword) || recipe.instructions.contains(keyword))

}