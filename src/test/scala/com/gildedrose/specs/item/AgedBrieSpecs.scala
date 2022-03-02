package com.gildedrose.specs.item

import com.gildedrose.specs.ItemDebug
import com.gildedrose.{GildedRose, Item}
import org.scalacheck.Prop._
import org.scalacheck._

object AgedBrieSpecs extends Properties("Aged Brie") with ItemDebug {

  val agedBrieGen: Gen[Item] = for {
    name <- Gen.const("Aged Brie")
    sellIn <- Gen.choose(-5, 150)
    quality <- Gen.choose(0, 50)
  } yield new Item(name, sellIn, quality)

  private val randomDays: Gen[Int] = Gen.choose(0, 100)

  property("Increment quality every day, once date has passed, increment twice") =
    forAll(agedBrieGen)(agedBrie => {
//      val app = Gil

//      agedBrie.quality

      true
    })
}
