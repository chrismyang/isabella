import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "isabella"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "se.radley" %% "play-plugins-salat" % "1.1",
      "javax.mail" % "mail" % "1.4.7",
      "org.jsoup" % "jsoup" % "1.7.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
