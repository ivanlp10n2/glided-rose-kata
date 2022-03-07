package com.gildedrose.item

import com.gildedrose.Item

object BackstagePass {
  val name = "Backstage passes to a TAFKAL80ETC concert"
  val isBackstage: Item => Boolean = _.name.startsWith("Backstage passes")

  def decrement(item: Item): Unit = {
    if (item.sellIn < 11) {
      if (isBelowMax(item)) {
        item.quality = item.quality + 1
      }
    }

    if (item.sellIn < 6) {
      if (isBelowMax(item)) {
        item.quality = item.quality + 1
      }
    }
  }

  private def isBelowMax(item: Item) = {
    item.quality < 50
  }

}
