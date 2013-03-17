package services

import models.{Location, Event}
import play.api.libs.concurrent.Promise
import play.api.libs.ws.WS
import play.api.libs.json.{JsValue, JsArray}
import play.api.libs.json.Reads._

class EventManager {

  def all = {
    val location = Location("55 Music Concourse Dr, San Francisco, CA 94118", 37.7697361, -122.46613809999997)
    val event = Event(Some("0000000"), "NightLife at the Academy of Science", location)

    val event2 = Event(Some("0000001"), "Bourbon & Branch", Location("501 Jones St, San Francisco, CA 94102", 37.785826,-122.413016))

    Seq(event, event2)
  }

  def create(title: String, locationText: String): Promise[Event] = {
    val base = """http://maps.google.com/maps/api/geocode/json"""
    val locationParam = "address=" + normalize(locationText)

    val requestUrl = base + "?sensor=false&" + locationParam

    WS.url(requestUrl).get() map { response =>
      val results = (response.json \ "results").as[Seq[JsValue]]

      val (lat, long) = if (results.size > 0) {
        val location = results(0) \ "geometry" \ "location"
        val latJs = location \ "lat"
        val longJs = location \ "lng"
        (latJs.as[Double], longJs.as[Double])
      } else {
        (0.0, 0.0)
      }

      Event(Some("000002"), title, Location(locationText, lat, long))
    }
  }

  private def normalize(text: String) = {
    text.replace(" ", "+")
  }
}

object EventManager {

}

object EventManagerService extends EventManager