package com.gildedrose

import com.gildedrose.item.AgedBrie.isAgedBrie
import com.gildedrose.item.BackstagePass
import com.gildedrose.item.BackstagePass.isBackstage
import com.gildedrose.item.Sulfuras.isSulfuras
import com.gildedrose.item.Conjured.isConjured
import com.gildedrose.item.Normal._


class GildedRose(val items: Array[Item]) {


  def updateQuality() {
    for (i <- 0 until items.length) {
      val item: Item = items(i)

      if (!isAgedBrie(item) && !isBackstage(item)) {
        if (isAboveMin(item) && !isSulfuras(item)) {
            item.quality = item.quality - 1
        }
      } else {
        if (isBelowMax(item)) {
          item.quality = item.quality + 1

          if (isBackstage(item)) {
            BackstagePass.decrement(item)
          }
        }
      }

      if (!isSulfuras(item)) {
        item.sellIn = item.sellIn - 1
      }

      if (hasDatePassed(item)) {
        if (!isAgedBrie(item)) {
          if (!isBackstage(item)) {
            if (isAboveMin(item)) {
              if (!isSulfuras(item)) {
                item.quality = item.quality - 1
              }
            }
          } else {
            item.quality = item.quality - item.quality
          }
        } else {
          if (isBelowMax(item)) {
            item.quality = item.quality + 1
          }
        }
      }

      if(isConjured(item)){
        if(isAboveMin(item)) {
          if (hasDatePassed(item)) {
            item.quality = item.quality - 2
          } else {
            item.quality = item.quality - 1
          }
          if (item.quality < 0)
            item.quality = 0
        }
      }
    }
  }

}