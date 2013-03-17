import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "isabella"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "se.radley" %% "play-plugins-salat" % "1.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
