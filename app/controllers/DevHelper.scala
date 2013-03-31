package controllers

import play.api.mvc.{Action, Controller}
import services.{EmailRequestProcessor, EventManagerService, EmailReceiver}
import play.api.libs.concurrent.Akka
import akka.actor.Props
import play.api.Play
import Play.current

object DevHelper extends Controller {

  def parseEmail = Action { implicit request =>
    val processor = Akka.system.actorOf(Props(new EmailRequestProcessor(new EmailReceiver, EventManagerService)))

    processor ! EmailRequestProcessor.Fetch

    Ok("")
  }
}
