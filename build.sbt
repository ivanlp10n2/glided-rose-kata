name := "GildedRose"

version := "1.0"

scalaVersion := "2.13.1"

resolvers += DefaultMavenRepository
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.1" % "test",
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.11.0" % "test")

fork in (Test, run) := true
