name := "GildedRose"

version := "1.0"

scalaVersion := "2.13.1"

resolvers += DefaultMavenRepository
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.1",
  "org.scalacheck" %% "scalacheck" % "1.14.1",
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.11.0",
  "org.specs2" %% "specs2-core" % "4.13.2",
  "org.specs2" %% "specs2-scalacheck" % "4.13.2"
)
  .map(_ % "test")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "5", "-minSuccessfulTests", "33", "-workers", "1", "-verbosity", "1")
fork in (Test, run) := true
