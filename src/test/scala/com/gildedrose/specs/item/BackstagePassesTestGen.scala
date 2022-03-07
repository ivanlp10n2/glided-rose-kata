package com.gildedrose.specs.item

import com.gildedrose.Item
import com.gildedrose.item.BackstagePass
import org.scalacheck.Gen

object BackstagePassesTestGen{
  val concertGen: Gen[Item] = for {
    name <- Gen.const(BackstagePass.name)
    sellIn <- Gen.choose(1, 50)
    quality <- Gen.choose(1, 50)
  } yield new Item(name, sellIn, quality)
}