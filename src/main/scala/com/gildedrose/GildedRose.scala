package com.gildedrose

import com.gildedrose.item.AgedBrie.isAgedBrie
import com.gildedrose.item.{AgedBrie, BackstagePass, Conjured, Sulfuras}
import com.gildedrose.item.BackstagePass.isBackstage
import com.gildedrose.item.Sulfuras.isSulfuras
import com.gildedrose.item.Conjured.isConjured
import com.gildedrose.item.Normal._


class GildedRose(val items: Array[Item]) {

  def updateQuality() {
    for (i <- 0 until items.length) {
      val item: Item = items(i)

      item match {
        case i if isAgedBrie(i) => AgedBrie.update(item)
        case i if isBackstage(i) => BackstagePass.update(item)
        case i if isSulfuras(i) => Sulfuras.update(item)
        case i if isConjured(i) => Conjured.update(item)
        case _ => { // NormalItem
          if (isAboveMin(item)) {
            item.quality = item.quality - 1
          }

          item.sellIn = item.sellIn - 1

          if (hasDatePassed(item)) {
            if (isAboveMin(item)) {
              item.quality = item.quality - 1
            }
          }
        }
      }

    }
  }

}