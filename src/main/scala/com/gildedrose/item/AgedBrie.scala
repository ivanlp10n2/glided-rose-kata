package com.gildedrose.item

import com.gildedrose.Item

object AgedBrie {
  val name ="Aged Brie"
  def isAgedBrie: Item => Boolean = _.name.startsWith("Aged Brie")
}
