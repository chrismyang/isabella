package models

import play.api.libs.json._
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import org.bson.types.ObjectId
import util.JsonFormats._

case class Event(
    _id: Option[ObjectId],
    title: String,
    imageUrl: Option[String],
    location: Location) {

  def withNewLatLong(newLatLong: LatLong) = {
    val newLocation = this.location.copy(latLong = newLatLong)
    this.copy(location = newLocation)
  }
}

case class Location(text: String, latLong: LatLong)

object Location {
  implicit object Format extends Format[Location] {
    def writes(o: Location) = JsObject(Seq(
      "text" -> JsString(o.text),
      "latitude" -> Json.toJson(o.latLong.latitude),
      "longitude" -> Json.toJson(o.latLong.longitude)
    ))

    def reads(json: JsValue) = Location(
      (json \ "text").as[String],
      LatLong(
        latitude = (json \ "latitude").as[Double],
        longitude = (json \ "longitude").as[Double]
      )
    )
  }
}

object Event {
  implicit object Format extends Format[Event] {
    def writes(o: Event) = JsObject(Seq(
      "id" -> Json.toJson(o._id),
      "title" -> JsString(o.title),
      "imageUrl" -> o.imageUrl.map(JsString(_)).getOrElse(JsNull),
      "location" -> Json.toJson(o.location)))

    def reads(json: JsValue) = {
      Event(
        _id = (json \ "id").as[Option[ObjectId]],
        title = (json \ "title").as[String],
        imageUrl = (json \ "imageUrl").asOpt[String],
        location = (json \ "location").as[Location]
      )
    }
  }
}
