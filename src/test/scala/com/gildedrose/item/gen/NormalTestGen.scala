package com.gildedrose.item.gen

import com.gildedrose.Item
import org.scalacheck._

object NormalTestGen {
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
