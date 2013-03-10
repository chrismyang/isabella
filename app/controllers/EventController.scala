package controllers

import play.api.mvc.{Action, Controller}
import models.Event
import play.api.libs.json.{Writes, Json}

object EventController extends Controller {
  def all = Action {
    val events = Seq(Event(None, "NightLife at the Academy of Science", "55 Music Concourse Dr, San Francisco, CA 94118"))
    Ok(Json.toJson(events)(Writes.seqWrites(Event.Format)))
  }
}
