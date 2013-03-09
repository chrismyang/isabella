package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def helloWorld = Action {
    val apiKey = "AIzaSyDZRRm-VCkNDrT6WmuxILqz7Kt8qFprSeY"
    Ok(views.html.helloWorld(apiKey))
  }
  
}