package com.gildedrose.item

import com.gildedrose.{GildedRose, Item}

trait ItemHelper {

  def times[A](n: Int)(f: Int => A): Unit =
    (1 to n).foreach(f)

  def runAction(app: GildedRose): (Item, Item) = {
    val previous = copyHead(app.items)
    app.updateQuality()
    val current = app.items.head
    (previous, current)
  }

  def debug(info: String, item: Item, day: Int, days: Int) = {
    println(s"------------ DAY : $day/$days ------------")
    println(s" INFO: $info")
    printItem(item)
    println(s"------------------------------------")
  }

  private def printItem: Item => Unit = item =>
    println(
      s" Item: ${item.name} \n" +
        s" quality: ${item.quality} \n" +
        s" sellIn: ${item.sellIn} "
    )

  def copyHead(items: Array[Item]) = {
    val item = items.head
    new Item(item.name, item.sellIn, item.quality)
  }

}
