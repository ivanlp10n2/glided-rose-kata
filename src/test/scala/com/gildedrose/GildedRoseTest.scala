package com.gildedrose

import com.gildedrose.item.ItemHelper
import com.gildedrose.item.gen._
import org.scalacheck.Gen
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.{Checkers, ScalaCheckDrivenPropertyChecks}

class GildedRoseTest extends AnyWordSpec with Checkers with ScalaCheckDrivenPropertyChecks with Matchers with ItemHelper {
  "A normal item" when {
    "update quality" should {
      "decrement quality by one, once the sell by date has passed, two" in {
        forAll(NormalItemTestGen.normalGen, NormalItemTestGen.randomDays) { (item, days) =>
          val items = Array[Item](item)
          val app = new GildedRose(items)

          def hasDatePassed: Item => Boolean = _.sellIn <= 0

          times(days){ day => {

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

          }}
        }
      }
    }
  }

  "A conjured item" when{
    "update quality" should {
      "decrement quality by two, once the sell by date has passed, four" in {
        forAll(ConjuredItemTestGen.conjuredGen, ConjuredItemTestGen.randomDays) { (item, days) =>
          val items = Array[Item](item)
          val app = new GildedRose(items)

          def hasDatePassed: Item => Boolean = _.sellIn <= 0

          times(days){ day => {

            val (prevItem, currItem) = runAction(app)

            debug("Previous", prevItem, day, days)
            debug("Current", currItem, day, days)
            currItem.sellIn shouldBe prevItem.sellIn - 1
            if (prevItem.quality >= 0 && currItem.quality == 0) succeed
            else {
              if (hasDatePassed(prevItem)) {
                currItem.quality shouldBe prevItem.quality - 4
              } else {
                currItem.quality shouldBe prevItem.quality - 2
              }
            }

          }}
        }
      }
      }

  }

  " Sulfuras, Hand of Ragnaros item" when {
    "update quality" should {
      "never change its quality" in {
        forAll(SulfurasTestGen.sulfurasGen, SulfurasTestGen.randomDay) {
          (sulfuras, randomDay) => {
            val app = new GildedRose(Array[Item](sulfuras))
            times(randomDay)(_ => {
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

            times(days) { day => {
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

  "A backstage pass item" when {
    "update quality" should {
      "increment quality by 2 when (10 > sellIn > 5) " in {
        val customGen = BackstagePassesTestGen.concertGen.flatMap(item => {
          val lowRange = Gen.choose(5, 10)
          lowRange.map(num => {
            item.sellIn = num
            item
          })
        })

        forAll(customGen) { item =>
          whenever(item.sellIn < 10 && item.sellIn > 5) {
            val app = new GildedRose(Array(item))
            val (prev, curr) = runAction(app)

            if ((prev.quality == 50) || (prev.quality <= 50 && curr.quality == 50)) {
              succeed
            } else {
              prev.quality + 2 shouldBe curr.quality
            }
          }
        }
      }

      "increment quality by 3 when (5 > sellIn > 0) " in {
        val customGen = BackstagePassesTestGen.concertGen.flatMap(item => {
          val lowRange = Gen.choose(0, 5)
          lowRange.map(num => {
            item.sellIn = num
            item
          })
        })

        forAll(customGen) { item =>
          whenever(item.sellIn < 5 && item.sellIn > 0) {
            val app = new GildedRose(Array(item))
            val (prev, curr) = runAction(app)

            if ((prev.quality == 50) || (prev.quality <= 50 && curr.quality == 50)) {
              succeed
            } else {
              prev.quality + 3 shouldBe curr.quality
            }
          }
        }
      }

      "set quality to 0 when (sellIn <= 0)" in {
        val customGen = BackstagePassesTestGen.concertGen.flatMap(item => {
          val lowRange = Gen.choose(-3, 0)
          lowRange.map(num => {
            item.sellIn = num
            item
          })
        })

        forAll(customGen) { item =>
          val app = new GildedRose(Array(item))
          val (_, curr) = runAction(app)

          curr.quality shouldBe 0
        }
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

      "an item quality should never goes below zero" in {
        forAll(RandomItemTestGen.genRandomItem, NormalItemTestGen.randomDays) { (item, days) => {
          val app = new GildedRose(Array[Item](item))

          times(days)(_ => app.updateQuality())

          app.items.head.quality >= 0
        }
        }
      }

      "an item can never have its Quality increase above 50" in {
        val itemsThatIncreasesItsQuality = Gen.oneOf(AgedBrieTestGen.agedBrieGen, BackstagePassesTestGen.concertGen)

        forAll(itemsThatIncreasesItsQuality, NormalItemTestGen.randomDays) { (item, days) =>
          val app = new GildedRose(Array[Item](item))
          times(days)(_ => {
            val (previous, current) = runAction(app)

            previous.quality should be <= 50
            current.quality should be <= 50
          })
        }
      }
    }
  }

}