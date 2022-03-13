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

    items.foreach( item => {
      val update = possibleItems
        .collectFirst { case (name, f) if (item.name startsWith name) => f }
        .getOrElse(default = Normal.update)

      update(item)
    })
   }

}
//      .find { case (name, _) => item.name.startsWith(name) }
//      .fold(ifEmpty = Normal.update)(_._2)
//      .apply(item)
