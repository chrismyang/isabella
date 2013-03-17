package models

import play.api.libs.json.{JsValue, Reads}

case class LatLong(latitude: Double, longitude: Double)

object LatLong {
  implicit object Format extends Reads[LatLong] {
    def reads(json: JsValue) = {
      val latJs = json \ "lat"
      val longJs = json \ "lng"

      LatLong(latJs.as[Double], longJs.as[Double])
    }
  }
}