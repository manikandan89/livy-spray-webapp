resolvers ++= Seq(
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
    "Sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/"
    )

    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.9.0")
