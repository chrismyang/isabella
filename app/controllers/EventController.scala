package controllers

import play.api.mvc.{Action, Controller}
import models.Event
import play.api.libs.json.{Writes, Json}
import services.EventManagerService

object EventController extends Controller {

  def all = Action {
    val events = EventManagerService.all
    Ok(Json.toJson(events)(Writes.seqWrites(Event.Format)))
  }

  def create = Action { implicit request =>
    val response = for {
      json <- request.body.asJson
    } yield {

      val title = (json \ "title").as[String]
      val locationText = (json \ "location" \ "text").as[String]

      Async {
        EventManagerService.create(title, locationText) map { event =>
          Ok(Json.toJson(event))
        }
      }
    }
    response.getOrElse(BadRequest("No json body"))
  }

  def update(id: String) = Action { implicit request =>
    val response = for {
      json <- request.body.asJson
    } yield {
      val updatedEvent = json.as[Event]
      EventManagerService.update(updatedEvent)
      Ok("Great")
    }

    response.getOrElse(BadRequest("No json body"))
  }
}
