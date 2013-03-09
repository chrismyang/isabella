package views

import controllers.routes
import play.api.Play
import Play.current
import scala.xml.Text

object ViewHelper {
  def requireMain(appFolderName: String, mainJsName: String) = {
    val requireFileName = "require-2.1.2" + (if (Play.isDev) "" else ".min")
    val requirePath = routes.Assets.at("thirdparty/" + requireFileName + ".js").url

    val baseUrlOverride = (<script />).copy(child = Seq(Text("""require.baseUrl = require.baseUrl + '/' + '%s';""".format(appFolderName))))
    val main = <script data-main={mainJsName} src={requirePath} />

    baseUrlOverride ++ main
  }
}
