package com.gildedrose.specs.item

import com.gildedrose.Item
import org.scalacheck.Gen

object RandomItem {
  val genRandomItem: Gen[Item] =
    Gen.oneOf(
      AgedBrieSpecs.agedBrieGen,
      NormalItemSpecs.normalGen,
      SulfurasSpecs.sulfurasGen
    )

}
