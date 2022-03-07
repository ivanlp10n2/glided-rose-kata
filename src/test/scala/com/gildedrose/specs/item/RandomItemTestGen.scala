package com.gildedrose.specs.item

import com.gildedrose.Item
import org.scalacheck.Gen

object RandomItemTestGen {
  val genRandomItem: Gen[Item] =
    Gen.oneOf(
      AgedBrieTestGen.agedBrieGen,
      NormalItemTestGen.normalGen,
      SulfurasTestGen.sulfurasGen
    )

}
