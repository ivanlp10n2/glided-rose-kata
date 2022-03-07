package com.gildedrose.item

import com.gildedrose.Item

object Conjured {
  val name = "Conjured"
  val isConjured: Item => Boolean = _.name.startsWith(name)

}
