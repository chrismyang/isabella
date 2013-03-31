package controllers

import play.api._
import play.api.mvc._
import services.EmailReceiver

object Application extends Controller {
  
  def index = Action {
    new EmailReceiver().doSomething()
    Ok("")
  }

  def helloWorld = Action {
    val apiKey = "AIzaSyDZRRm-VCkNDrT6WmuxILqz7Kt8qFprSeY"
    Ok(views.html.helloWorld(apiKey))
  }
  
}