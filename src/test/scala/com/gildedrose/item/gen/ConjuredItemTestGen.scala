package com.gildedrose.item.gen

import com.gildedrose.Item
import com.gildedrose.item.Conjured
import org.scalacheck._

object ConjuredItemTestGen {
    val conjuredName: Gen[String] = for {
      head <- Gen.const(Conjured.name)
      tail <- Gen.listOfN(10, Gen.alphaChar).map(_.mkString)
      name = s"$head $tail"
    } yield name

    val conjuredGen: Gen[Item] = for {
      name    <- conjuredName
      sellIn  <- Gen.choose(-5, 150)
      quality <- Gen.choose(0, 50)
    } yield new Item(name, sellIn, quality)

    val randomDays: Gen[Int] = Gen.choose(0, 100)

}
