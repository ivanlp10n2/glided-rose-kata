package com.gildedrose.item

import com.gildedrose.Item

object AgedBrie {
  val name ="Aged Brie"
  val isAgedBrie: Item => Boolean = _.name.startsWith("Aged Brie")
}
