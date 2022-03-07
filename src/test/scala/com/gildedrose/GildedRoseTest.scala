package com.gildedrose

import com.gildedrose.specs.item.{AgedBrieTestGen, NormalItemTestGen, RandomItemTestGen, SulfurasTestGen}
import com.gildedrose.specs.{ItemDebug, SpecsUtils}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.{Checkers, ScalaCheckDrivenPropertyChecks}

class GildedRoseTest extends AnyWordSpec with Checkers with ScalaCheckDrivenPropertyChecks with Matchers with SpecsUtils with ItemDebug {
  "A normal item" when {
    "update quality" should {
      "decrement quality by one, once the sell by date has passed, two" in {
        forAll(NormalItemTestGen.normalGen, NormalItemTestGen.randomDays) { (item, days) =>
          val items = Array[Item](item)
          val app = new GildedRose(items)

          def hasDatePassed: Item => Boolean = _.sellIn <= 0

          1 to days foreach { day => {

            val (prevItem, currItem) = runAction(app)

            currItem.sellIn shouldBe prevItem.sellIn - 1
            if (currItem.quality == 0 || currItem.quality == 50) succeed
            else {
              if (hasDatePassed(prevItem)) {
                currItem.quality shouldBe prevItem.quality - 2
              } else {
                currItem.quality shouldBe prevItem.quality - 1
              }
            }

          }
          }
        }
      }
    }
  }

  "A Sulfuras, Hand of Ragnaros item" when {
    "update quality" should {
      "never change its quality" in {
        forAll(SulfurasTestGen.sulfurasGen, SulfurasTestGen.randomDay) {
          (sulfuras, randomDay) => {
            val app = new GildedRose(Array[Item](sulfuras))
            (1 to randomDay).foreach(_ => {
              val (previous, current) = runAction(app)
              previous.quality shouldBe current.quality
            })
          }
        }
      }
    }
  }

  "An Aged Brie item" when {
    "update quality" should {
      "increment quality by one, once date has passed, increment twice" in {
        forAll(AgedBrieTestGen.agedBrieGen, AgedBrieTestGen.randomDays)((agedBrie, days) => {
          whenever(agedBrie.sellIn > 0) {
            val items = Array[Item](agedBrie)
            val app = new GildedRose(items)

            def hasDatePassed: Item => Boolean = _.sellIn <= 0

            1 to days foreach { day => {
              val (previous, current) = runAction(app)

              current.sellIn shouldBe previous.sellIn - 1
              if ((previous.quality == 50) || (previous.quality <= 50 && current.quality == 50)) {
                succeed
              }
              else {
                if (hasDatePassed(previous)) {
                  current.quality shouldBe previous.quality + 2
                }
                else {
                  current.quality shouldBe previous.quality + 1
                }
              }

            }
            }
          }
        })
      }
    }
  }

  "Any item" when {
    "update quality" should {
      "returns the item name" in {
        forAll(RandomItemTestGen.genRandomItem) { item =>
          val items = Array[Item](item)
          val app = new GildedRose(items)
          app.updateQuality()
          app.items(0).name shouldBe item.name
        }
      }

      "do nothing if items are empty" in {
        val items = Array[Item]()
        val app = new GildedRose(items)
        app.updateQuality()
        app.items shouldBe Array.empty[Item]
      }

      "normal item quality should never goes below zero" in {
        val prefix = "normal item"
        val property = "quality of a normal item never goes below zero"

        val propertyName = s"$prefix.$property"
        findProperty(NormalItemTestGen)(propertyName).check()
      }

    }
  }

}