package data

import com.novus.salat.dao._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import com.novus.salat.{TypeHintFrequency, StringTypeHintStrategy, Context}
import play.api.Play
import play.api.Play.current
import com.novus.salat.json.{StringObjectIdStrategy, JSONConfig}

/*
 * This custom context is necessary for a few reasons, but probably most importantly to prevent a deadlock issue
 * around the Play classloader and scheduled tasks:
 *
 * http://ska-la.blogspot.com.br/2012/03/play-20-and-salat-mongodb-dao-provider.html
 * http://play.lighthouseapp.com/projects/82401/tickets/470-deadlock-in-dev-mode
 */
package object mongoContext {
  def baseContext = {
    val context = new Context {
      val name = "global"
      override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary, typeHint = "_t")
      override val jsonConfig = JSONConfig(objectIdStrategy = StringObjectIdStrategy)
    }
    context.registerGlobalKeyOverride(remapThis = "id", toThisInstead = "_id")
    context
  }

  implicit lazy val prodContext = {
    baseContext.registerClassLoader(Play.classloader)
    baseContext
  }
}
package object data {

}
