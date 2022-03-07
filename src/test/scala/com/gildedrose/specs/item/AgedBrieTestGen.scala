package com.gildedrose.specs.item

import com.gildedrose.Item
import com.gildedrose.specs.ItemDebug
import org.scalacheck._

object AgedBrieTestGen {

  val agedBrieGen: Gen[Item] = for {
    name <- Gen.const("Aged Brie")
    sellIn <- Gen.choose(-5, 150)
    quality <- Gen.choose(0, 50)
  } yield new Item(name, sellIn, quality)

  val randomDays: Gen[Int] = Gen.choose(0, 100)

}
