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
}
