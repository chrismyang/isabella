package models

import play.api.libs.json._
import play.api.libs.json.JsObject
import play.api.libs.json.JsString

case class Event(
    _id: Option[String],
    title: String,
    location: Location) {

}

case class Location(text: String, latLong: LatLong)

object Location {
  implicit object Format extends Writes[Location] {
    def writes(o: Location) = JsObject(Seq(
      "text" -> JsString(o.text),
      "latitude" -> Json.toJson(o.latLong.latitude),
      "longitude" -> Json.toJson(o.latLong.longitude)
    ))
  }
}

object Event {
  implicit object Format extends Writes[Event] {
    def writes(o: Event) = JsObject(Seq(
      "id" -> o._id.map(JsString(_)).getOrElse(JsNull),
      "title" -> JsString(o.title),
      "location" -> Json.toJson(o.location)))
  }
}
