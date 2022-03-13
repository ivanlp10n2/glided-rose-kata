package com.gildedrose.item

import com.gildedrose.Item
import com.gildedrose.item.Normal.hasDatePassed

object BackstagePass {
  val name = "Backstage passes to a TAFKAL80ETC concert"
//  def isBackstage: Item => Boolean = _.name.startsWith("Backstage passes

  def update(item: Item):Unit = {

    if (isBelowMax(item)) {
      item.quality = item.quality + 1
      BackstagePass.updateQuality(item)
    }

    item.sellIn = item.sellIn - 1

    if (hasDatePassed(item)) {
      item.quality = item.quality - item.quality
    }

  }
  private def updateQuality(item: Item): Unit = {
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
