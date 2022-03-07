package com.gildedrose.specs.item

import com.gildedrose.specs.ItemDebug
import com.gildedrose.{GildedRose, Item, TestUtils}
import org.scalacheck._
import org.scalacheck.Prop._

object NormalItemTestGen extends Properties("normal item") with ItemDebug with TestUtils{
    val itemNameGen: Gen[String] = for {
      head <- Gen.alphaUpperChar
      tail <- Gen.alphaStr
      name = s"$head$tail"
    } yield name

    val normalGen: Gen[Item] = for {
      name    <- itemNameGen
      sellIn  <- Gen.choose(-5, 150)
      quality <- Gen.choose(0, 50)
    } yield new Item(name, sellIn, quality)

    val randomDays: Gen[Int] = Gen.choose(0, 100)

}
