package com.gildedrose.item

import com.gildedrose.Item

object Normal {

  def hasDatePassed(item: Item): Boolean = {
    item.sellIn < 0
  }

  def isBelowMax(item: Item): Boolean = {
    item.quality < 50
  }

  def isAboveMin(item: Item): Boolean = {
    item.quality > 0
  }

  def update:  Item => Unit = item =>{
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
