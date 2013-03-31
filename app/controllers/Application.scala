package controllers

import play.api._
import play.api.mvc._
import services.EmailReceiver
import templates.Html

object Application extends Controller {
  
  def index = Action {
    val emails = new EmailReceiver().fetchNewMessages(deleteAfterReading = false)
    Ok(Html("<ul>%s</ul>".format(emails.map("<li>" + _ + "</li>").mkString("\n"))))
  }

  def helloWorld = Action {
    val apiKey = "AIzaSyDZRRm-VCkNDrT6WmuxILqz7Kt8qFprSeY"
    Ok(views.html.helloWorld(apiKey))
  }
  
}