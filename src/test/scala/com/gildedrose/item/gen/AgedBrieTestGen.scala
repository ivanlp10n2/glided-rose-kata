package com.gildedrose.item.gen

import com.gildedrose.Item
import com.gildedrose.item.AgedBrie
import com.gildedrose.item.ItemHelper
import org.scalacheck._

object AgedBrieTestGen {

  val agedBrieGen: Gen[Item] = for {
    name <- Gen.const(AgedBrie.name)
    sellIn <- Gen.choose(-5, 150)
    quality <- Gen.choose(0, 50)
  } yield new Item(name, sellIn, quality)

  val randomDays: Gen[Int] = Gen.choose(0, 100)

}
