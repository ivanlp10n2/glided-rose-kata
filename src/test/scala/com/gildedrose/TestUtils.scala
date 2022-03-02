package com.gildedrose

import scala.annotation.tailrec

trait TestUtils{

    def times[A](n: Int)(f: Int => A): Unit = {
      @tailrec
      def recursive(n: Int)(f: Int => A)(acc: Int): Unit =
        if (acc > n) ()
        else {
          f(acc)
          recursive(n)(f)(acc + 1)
        }

      recursive(n)(f)(1)
    }
}