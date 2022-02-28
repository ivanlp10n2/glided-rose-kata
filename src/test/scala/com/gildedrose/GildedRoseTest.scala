package com.gildedrose

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GildedRoseTest extends AnyWordSpec with Matchers {
  "Gilded rose" when {
    "update quality" should {
      "returns the item name" in {
        val items = Array[Item](new Item("foo", 0, 0))
        val app = new GildedRose(items)
        app.updateQuality()
        app.items(0).name should equal("foo")
      }

      "do nothing if items are empty" in {
        val items = Array[Item]()
        val app = new GildedRose(items)
        app.updateQuality()
        app.items shouldBe Array.empty[Item]
      }

      "A normal item should decrement quality and sell days by one when available" in {
        val days = 10
        val maxQuality = 50

        val normalItem = new Item("normal item", days, maxQuality)
        val items = Array[Item](normalItem)
        val app = new GildedRose(items)
        1 to days foreach { day => {
          val prevItem: Item = {
            val item = app.items.head
            new Item(item.name, item.sellIn, item.quality)
          }

          app.updateQuality()
          val currItem = app.items.head

          printComparission(day, prevItem, currItem)
          currItem.quality shouldBe prevItem.quality - 1
          currItem.sellIn shouldBe prevItem.sellIn - 1
        }
        }
      }

      "Days of Aged Brie decreases by 1 and quality increases by 1 while available" in{
        val days = 30
        val items = Array[Item](new Item("Aged Brie", days, 0))
        val app = new GildedRose(items)

        1 to days foreach { _ => {
          val previous = {
            val item = app.items.head
            new Item(item.name, item.sellIn, item.quality)
          }
          app.updateQuality()
          val current = app.items.head
          current.sellIn shouldBe previous.sellIn - 1
          current.quality shouldBe previous.quality + 1
        }}
      }

      "Quality of Aged Brie never reaches bigger than 50 during 80 days" in {
        val days = 80
        val initialQuality = 0
        val items = Array[Item](new Item("Aged Brie", days, initialQuality))

        val app = new GildedRose(items)
        (1 to days).foreach { _ => {
          app.updateQuality()
          val agedBrie = app.items.head

          if (agedBrie.quality > 50) {
            fail("Quality of Aged Brie cannot be > 50")
          }
        }}
      }


    }

  }

  def printComparission(day: Int, prevItem: Item, currItem: Item) = {
    println("------------------------------------------------")
    println(s"prev: quality: ${prevItem.quality} --- sellIn: ${prevItem.sellIn}")
    println(s"=======Day $day=========")
    println(s"after: quality: ${currItem.quality} --- sellIn: ${currItem.sellIn}")
    println("------------------------------------------------")
  }


}