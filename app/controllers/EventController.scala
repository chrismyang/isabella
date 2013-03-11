package controllers

import play.api.mvc.{Action, Controller}
import models.{Location, Event}
import play.api.libs.json.{Writes, Json}

object EventController extends Controller {
  def all = Action {
    val location = Location("55 Music Concourse Dr, San Francisco, CA 94118", 37.7697361, -122.46613809999997)
    val event = Event(None, "NightLife at the Academy of Science", location)

    val event2 = Event(None, "Bourbon & Branch", Location("501 Jones St, San Francisco, CA 94102", 37.785826,-122.413016))

    val events = Seq(event, event2)
    Ok(Json.toJson(events)(Writes.seqWrites(Event.Format)))
  }
}
