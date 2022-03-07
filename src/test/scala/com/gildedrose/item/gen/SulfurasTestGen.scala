package com.gildedrose.item.gen

import com.gildedrose.Item
import com.gildedrose.item.Sulfuras
import com.gildedrose.item.ItemHelper
import org.scalacheck._

object SulfurasTestGen {
  val sulfurasGen: Gen[Item] = for {
    name <- Gen.const(Sulfuras.name)
    sellIn <- Gen.choose(0, 200)
    quality <- Gen.const(Sulfuras.constantQuality)
  } yield new Item(name, sellIn, quality)

  val randomDay: Gen[Int] = Gen.choose(1, 50)


}
