package util

import org.bson.types.ObjectId
import play.api.libs.json._
import play.api.libs.json.JsUndefined
import play.api.libs.json.JsString
import scala.Some

object JsonFormats {
  implicit object ObjectIdFormat extends Format[ObjectId] {
    def reads(json: JsValue) = new ObjectId(json.as[String])
    def writes(o: ObjectId) = JsString(o.toString)
  }

  implicit object OptionObjectIdFormat extends Format[Option[ObjectId]] {
    def reads(json: JsValue): Option[ObjectId] = {
      json match {
        case JsNull => None
        case _: JsUndefined => None
        case x => Some(json.as[ObjectId])
      }
    }
    def writes(o: Option[ObjectId]): JsValue = o.map(Json.toJson(_)).getOrElse(JsNull)
  }

  implicit val seqOfIds = Reads.seqReads(ObjectIdFormat)
}
