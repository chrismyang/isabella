package services

import models.{Location, Event}
import play.api.libs.concurrent.Promise

class EventManager {

  def all = {
    val location = Location("55 Music Concourse Dr, San Francisco, CA 94118", 37.7697361, -122.46613809999997)
    val event = Event(Some("0000000"), "NightLife at the Academy of Science", location)

    val event2 = Event(Some("0000001"), "Bourbon & Branch", Location("501 Jones St, San Francisco, CA 94102", 37.785826,-122.413016))

    Seq(event, event2)
  }

  def create(title: String, locationText: String): Promise[Event] = {
    Promise.pure(Event(Some("000002"), title, Location(locationText, 0.0, 0.0)))
  }
}

object EventManager {

}

object EventManagerService extends EventManager