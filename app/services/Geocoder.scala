package services

import play.api.libs.concurrent.Promise
import play.api.libs.ws.WS
import play.api.libs.json.{Reads, JsValue}
import models.LatLong

class Geocoder {
  import Geocoder._

  def geocode(address: String): Promise[Option[LatLong]] = {
    val requestUrl = makeRequestUrl(address)
    WS.url(requestUrl).get() map { response =>

      val results = (response.json \ "results").as[Seq[JsValue]]

      for {
        result <- results.headOption
      } yield {
        val location = result \ "geometry" \ "location"
        location.as[LatLong]
      }
    }
  }
}

object Geocoder {

  private def makeRequestUrl(location: String) = {
    val base = """http://maps.google.com/maps/api/geocode/json"""
    val locationParam = "address=" + normalize(location)

    base + "?sensor=false&" + locationParam
  }

  private def normalize(text: String) = {
    text.replace(" ", "+")
  }
}

