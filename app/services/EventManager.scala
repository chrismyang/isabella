package services

import models.{LatLong, Location, Event}
import play.api.libs.concurrent.Promise
import data.EventDAO
import com.mongodb.casbah.commons.MongoDBObject

class EventManager {

  private val geocoder = new Geocoder

  def all = {
    EventDAO.findAll().toSeq
  }

  def create(title: String, locationText: String): Promise[Event] = {
    geocoder.geocode(locationText) map {
      latLongOpt =>
        val latLong = latLongOpt.getOrElse(LatLong(0.0, 0.0))
        val eventToSave = Event(None, title, Location(locationText, latLong))

        val id = EventDAO.insert(eventToSave)
        eventToSave.copy(_id = id)
    }
  }

  def update(eventToUpdate: Event) {
    val id = eventToUpdate._id.getOrElse(sys.error("Cannot update Event with no ID"))

    geocoder.geocode(eventToUpdate.location.text) map {
      latLongOpt =>
        val latLong = latLongOpt.getOrElse(LatLong(0.0, 0.0))
        val geoCodedEvent = eventToUpdate.withNewLatLong(latLong)

        val byId = MongoDBObject("_id" -> id)
        EventDAO.update(byId, geoCodedEvent, upsert = false, multi = false, wc = EventDAO.defaultWriteConcern)
    }
  }
}

object EventManager {

}

object EventManagerService extends EventManager