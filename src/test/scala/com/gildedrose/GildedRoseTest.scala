package com.gildedrose

import com.gildedrose.specs.item.{NormalItemSpecs, SulfurasSpecs}
import com.gildedrose.specs.{ItemDebug, SpecsUtils}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.{Checkers, ScalaCheckDrivenPropertyChecks}

class GildedRoseTest extends AnyWordSpec with Checkers with ScalaCheckDrivenPropertyChecks with Matchers with SpecsUtils with ItemDebug {
  "A normal item" when {
    "update quality" should {
      "decrement quality by one, once the sell by date has passed, two" in {
        forAll(NormalItemSpecs.normalGen, NormalItemSpecs.randomDays) { (item, days) =>
          val items = Array[Item](item)
          val app = new GildedRose(items)
          1 to days foreach { day => {

            val (prevItem, currItem) = runAction(app)

            if (currItem.quality == 0) succeed
            else {
              if (currItem.sellIn >= 0) {
                currItem.quality shouldBe prevItem.quality - 1
                currItem.sellIn shouldBe prevItem.sellIn - 1
              }
              else {
                currItem.quality shouldBe prevItem.quality - 2
                currItem.sellIn shouldBe prevItem.sellIn - 1
              }
            }
          }
          }
        }
      }
    }
  }

  "A Sulfuras, Hand of Ragnaros" when {
    val prefix = "Sulfuras, Hand of Ragnaros"
    //    val propertyName: String => String = property => s"$prefix.$property"
    "update quality" should {
      "never change its quality" in {
        forAll(SulfurasSpecs.sulfurasGen, SulfurasSpecs.randomDay) {
          (sulfuras, randomDay) => {
            val app = new GildedRose(Array[Item](sulfuras))
            (1 to randomDay).foreach(_ => {
              val (previous, current) = runAction(app)
              previous.quality shouldBe current.quality
            })
          }
        }
        //        findProperty(SulfurasSpecs)(propertyName("never changes its quality"))
      }
    }
  }

  "Gilded rose" when {
    "update quality" should {
      //      "returns the item name" in {
      //        forAll(RandomItem.genRandomItem) { item =>
      //          val items = Array[Item](item)
      //          val app = new GildedRose(items)
      //          app.updateQuality()
      //          app.items(0).name shouldBe item.name
      //        }
      //
      //        "do nothing if items are empty" in {
      //          val items = Array[Item]()
      //          val app = new GildedRose(items)
      //          app.updateQuality()
      //          app.items shouldBe Array.empty[Item]
      //        }
      "An Aged Brie increases quality by 1 and twice once the sell by date has passed" in {
        //        findProperty(AgedBrieSpecs)
        val days = 30
        val items = Array[Item](new Item("Aged Brie", days, 0))
        val app = new GildedRose(items)

        1 to days foreach { _ => {
          val previous = copyHead(app.items)
          app.updateQuality()
          val current = app.items.head
          current.sellIn shouldBe previous.sellIn - 1
          current.quality shouldBe previous.quality + 1
        }
        }
      }


      "normal item quality should never goes below zero" in {
        val prefix = "normal item"
        val property = "quality of a normal item never goes below zero"

        val propertyName = s"$prefix.$property"
        findProperty(NormalItemSpecs)(propertyName).check()
      }
    }
  }


  def printComparission(day: Int, prevItem: Item, currItem: Item) = {
    println("------------------------------------------------")
    println(s"prev: quality: ${
      prevItem.quality
    } --- sellIn: ${
      prevItem.sellIn
    }")
    println(s"=======Day $day=========")
    println(s"after: quality: ${
      currItem.quality
    } --- sellIn: ${
      currItem.sellIn
    }")
    println("------------------------------------------------")
  }


}