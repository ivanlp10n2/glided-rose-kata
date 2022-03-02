package com.gildedrose.specs

import org.scalacheck.{Prop, Properties}
import org.scalatest.Assertions.fail

trait SpecsUtils {
  def findProperty(obj: Properties)(propertyName: String): Prop =
    obj.properties.collectFirst{
        case (name, property)
          if name == s"$propertyName" => property
      }.fold(fail("No property found")){identity}

  def checkProperty(obj: Properties)(propertyName: String): Unit =
    obj.properties.collectFirst{
      case (name, property)
        if name == s"$propertyName" => property
    }.fold(fail("No property found")){n => n.check()}

}
