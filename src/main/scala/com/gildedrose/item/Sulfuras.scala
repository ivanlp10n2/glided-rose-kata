package com.gildedrose.item

import com.gildedrose.Item

object Sulfuras {
  val name = "Sulfuras, Hand of Ragnaros"
  val constantQuality = 80

  def isSulfuras: Item => Boolean = _.name == name

}
