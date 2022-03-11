package com.gildedrose.item

import com.gildedrose.Item
import com.gildedrose.item.Normal.{hasDatePassed, isBelowMax}

object AgedBrie {
  val name = "Aged Brie"

  def isAgedBrie: Item => Boolean = _.name.startsWith("Aged Brie")

  def update: Item => Unit = item => {
    if (isBelowMax(item)) {
      item.quality = item.quality + 1
    }

    item.sellIn = item.sellIn - 1

    if (hasDatePassed(item)) {
      if (isBelowMax(item)) {
        item.quality = item.quality + 1
      }
    }
  }

}
