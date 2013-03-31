package controllers

import play.api.mvc.{Action, Controller}
import services.{EventManagerService, EmailReceiver}
import parser.EventParser

object DevHelper extends Controller {

  def parseEmail = Action { implicit request =>
    val emails = new EmailReceiver().fetchNewMessages()

    for {
      email <- emails
    } yield {
      val promise = new EventParser().parseFromEmail(email)
      promise onRedeem { event => EventManagerService.create(event) }
    }

    Ok("")
  }
}
