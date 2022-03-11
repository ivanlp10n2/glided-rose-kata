package com.gildedrose

import com.gildedrose.item._


class GildedRose(val items: Array[Item]) {

  def updateQuality() {
    val possibleItems: Map[String, Item => Unit] = Map(
      AgedBrie.name -> AgedBrie.update,
      BackstagePass.name -> BackstagePass.update,
      Sulfuras.name -> Sulfuras.update,
      Conjured.name -> Conjured.update
    )

    for (i <- 0 until items.length) {
      val item: Item = items(i)

      possibleItems
      .find { case (name, _) => item.name.startsWith(name) }
      .fold(ifEmpty = Normal.update)(_._2)
      .apply(item)
    }
  }

}