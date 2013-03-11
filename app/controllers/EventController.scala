package controllers

import play.api.mvc.{Action, Controller}
import models.{Location, Event}
import play.api.libs.json.{Writes, Json}

object EventController extends Controller {
  def all = Action {
    val location = Location("55 Music Concourse Dr, San Francisco, CA 94118", -25.363882, 131.044922)
    val events = Seq(Event(None, "NightLife at the Academy of Science", location))
    Ok(Json.toJson(events)(Writes.seqWrites(Event.Format)))
  }
}
