package com.lunatech.goldenalgo.onboarding.model

import com.sksamuel.elastic4s.{Hit, HitReader, Indexable}
import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._

import scala.util.Try

case class Recipe(id: String, name: String, ingredients: Seq[String], instructions: Seq[String])

object Recipe {
  implicit val codec: Codec[Recipe] = deriveCodec[Recipe]
}

package object MyImplicit{
  implicit object RecipeHitReader extends HitReader[Recipe] {
    override def read(hit: Hit): Try[Recipe] = {
      val source = hit.sourceAsMap
      Try(Recipe(source("id").toString, source("name").toString, source("ingredients").asInstanceOf[List[String]], source("instructions").asInstanceOf[List[String]]))
    }
  }
  implicit object RecipeIndexable extends Indexable[Recipe] {
    override def json(r: Recipe): String = r.asJson.noSpaces
  }
}