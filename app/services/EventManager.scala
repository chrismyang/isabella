package services

import models.{LatLong, Location, Event}
import play.api.libs.concurrent.Promise

class EventManager {

  private val geocoder = new Geocoder

  def all = {
    val location = Location("55 Music Concourse Dr, San Francisco, CA 94118", LatLong(37.7697361, -122.46613809999997))
    val event = Event(Some("0000000"), "NightLife at the Academy of Science", location)

    val event2 = Event(Some("0000001"), "Bourbon & Branch", Location("501 Jones St, San Francisco, CA 94102", LatLong(37.785826,-122.413016)))

    Seq(event, event2)
  }

  def create(title: String, locationText: String): Promise[Event] = {
    geocoder.geocode(locationText) map { latLongOpt =>
      val latLong = latLongOpt.getOrElse(LatLong(0.0, 0.0))
      Event(Some("000002"), title, Location(locationText, latLong))
    }
  }
}

object EventManager {

}

object EventManagerService extends EventManager