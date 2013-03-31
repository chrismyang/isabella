import play.api.GlobalSettings
import play.api.Application
import services.EmailRequestProcessor

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    EmailRequestProcessor.start()
  }
}