package data

import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import org.bson.types.ObjectId
import se.radley.plugin.salat._
import models.Event
import com.novus.salat._
import mongoContext._
import play.api.Play
import Play.current

object EventDAO extends ModelCompanion[Event, ObjectId] {
  val collection = mongoCollection("events")
  val dao = new SalatDAO[Event, ObjectId](collection = collection) {}
}