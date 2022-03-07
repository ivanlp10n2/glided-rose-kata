package com.gildedrose

class GildedRose(val items: Array[Item]) {


  def updateQuality() {
    for (i <- 0 until items.length) {
      val item: Item = items(i)

      if (!isAgedBrie(item) && !isBackstagePasses(item)) {
        if (isAboveMin(item) && !isSulfuras(item)) {
            item.quality = item.quality - 1
        }
      } else {
        if (isBelowMax(item)) {
          item.quality = item.quality + 1

          if (isBackstagePasses(item)) {
            updateBackstage(item)
          }
        }
      }

      if (!isSulfuras(item)) {
        item.sellIn = item.sellIn - 1
      }

      if (hasDatePassed(item)) {
        if (!isAgedBrie(item)) {
          if (!isBackstagePasses(item)) {
            if (isAboveMin(item)) {
              if (!isSulfuras(item)) {
                item.quality = item.quality - 1
              }
            }
          } else {
            item.quality = item.quality - item.quality
          }
        } else {
          if (isBelowMax(item)) {
            item.quality = item.quality + 1
          }
        }
      }

      if(isConjured(item)){
        if(isAboveMin(item)) {
          if (hasDatePassed(item)) {
            item.quality = item.quality - 2
          } else {
            item.quality = item.quality - 1
          }
          if (item.quality < 0)
            item.quality = 0
        }
      }
    }
  }

  private def hasDatePassed(item: Item) = {
    item.sellIn < 0
  }

  private def updateBackstage(item: Item) = {
    if (item.sellIn < 11) {
      if (isBelowMax(item)) {
        item.quality = item.quality + 1
      }
    }

    if (item.sellIn < 6) {
      if (isBelowMax(item)) {
        item.quality = item.quality + 1
      }
    }
  }

  private def isBelowMax(item: Item) = {
    item.quality < 50
  }

  private def isAboveMin(item: Item) = {
    item.quality > 0
  }

  private def isSulfuras(i: Item): Boolean = {
    i.name.equals("Sulfuras, Hand of Ragnaros")
  }

  private def isBackstagePasses(i: Item): Boolean = {
    i.name.startsWith("Backstage passes")
  }

  private def isAgedBrie(i: Item): Boolean = {
    i.name.startsWith("Aged Brie")
  }

  private def isConjured(i: Item): Boolean = i.name.startsWith("Conjured")
}