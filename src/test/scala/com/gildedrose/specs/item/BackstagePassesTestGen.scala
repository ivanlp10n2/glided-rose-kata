package com.gildedrose.specs.item

import com.gildedrose.Item
import org.scalacheck.Gen

object BackstagePassesTestGen{
  val concertGen: Gen[Item] = for {
    name <- Gen.const("Backstage passes to a TAFKAL80ETC concert")
    sellIn <- Gen.choose(1, 50)
    quality <- Gen.choose(1, 50)
  } yield new Item(name, sellIn, quality)
}