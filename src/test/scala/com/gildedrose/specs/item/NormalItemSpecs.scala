package com.gildedrose.specs.item

import com.gildedrose.specs.ItemDebug
import com.gildedrose.{GildedRose, Item, TestUtils}
import org.scalacheck._
import org.scalacheck.Prop._

object NormalItemSpecs extends Properties("normal item") with ItemDebug with TestUtils{
    val itemNameGen: Gen[String] = for {
      head <- Gen.alphaUpperChar
      tail <- Gen.alphaStr
      name = s"$head$tail"
    } yield name

    val normalGen: Gen[Item] = for {
      name    <- itemNameGen
      sellIn  <- Gen.choose(-5, 150)
      quality <- Gen.choose(0, 50)
    } yield new Item(name, sellIn, quality)

    val randomDays: Gen[Int] = Gen.choose(0, 100)


    property("quality of a normal item never goes below zero") = {
      forAll(normalGen, randomDays) { (item, days) => {
        val app = new GildedRose(Array[Item](item))

        times(days)(_ => app.updateQuality())

        app.items.head.quality >= 0
      }}
    }

    property("once the sell by date has passed, quality degrades twice as fast") = {
      val randomItem = Gen.choose(-30, 30)
        .flatMap(sellIn =>
          normalGen.map(item => {
            item.sellIn = sellIn
            item
          })
        )

      val degradationBeforePassed = 1
      val twiceDegradation = degradationBeforePassed * 2

      forAll(randomItem, randomDays) { (item, days) => {
        val app = new GildedRose(Array[Item](item))

        val runs: Seq[Int] = (1 to days) takeWhile { day =>
          val (previous, current) = runAction(app)

          if (current.quality == 0 || previous.quality == 0) {
            true
          } else{
            if (previous.sellIn > 0) {
              current.quality == (previous.quality - degradationBeforePassed)
          }
          else {
            current.quality == (previous.quality - twiceDegradation)
          }}
        }

        runs.sizeIs == days
      }}
    }

}
