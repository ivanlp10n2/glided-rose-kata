package com.gildedrose.item.gen

import com.gildedrose.Item
import org.scalacheck.Gen

object RandomTestGen {
  val genRandomItem: Gen[Item] =
    Gen.oneOf(
      AgedBrieTestGen.agedBrieGen,
      NormalTestGen.normalGen,
      SulfurasTestGen.sulfurasGen,
      ConjuredTestGen.conjuredGen
    )

}
