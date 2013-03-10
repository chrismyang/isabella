package models

import play.api.libs.json.{JsString, JsNull, JsObject, Writes}

case class Event(
    _id: Option[String],
    title: String,
    location: String) {

}

object Event {
  implicit object Format extends Writes[Event] {
    def writes(o: Event) = JsObject(Seq(
      "id" -> o._id.map(JsString(_)).getOrElse(JsNull),
      "title" -> JsString(o.title),
      "location" -> JsString(o.location)))
  }
}
