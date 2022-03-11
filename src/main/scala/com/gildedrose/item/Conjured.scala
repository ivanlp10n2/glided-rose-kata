package com.gildedrose.item

import com.gildedrose.Item
import com.gildedrose.item.Normal.{hasDatePassed, isAboveMin}

object Conjured {
  def update(item: Item) = {
    if (isAboveMin(item)) {
      item.quality = item.quality - 1
    }

    item.sellIn = item.sellIn - 1

    if (hasDatePassed(item)) {
      if (isAboveMin(item)) {
        item.quality = item.quality - 1
      }
    }

    if (isAboveMin(item)) {
      if (hasDatePassed(item)) {
        item.quality = item.quality - 2
      } else {
        item.quality = item.quality - 1
      }
      if (item.quality < 0)
        item.quality = 0
    }
  }


  val name = "Conjured"

  def isConjured: Item => Boolean = _.name.startsWith(name)

}
