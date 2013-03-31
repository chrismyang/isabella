package parser

import models.{Location, Event}
import services.Email

class EventParser {

  def parseFromEmail(email: Email): Event = {
    val title = email.subject
    val notes = Some(email.body)

    Event(None, title, None, None, notes, Location("foo", null))
  }

  def parseFromLink(link: String): Event = {
    null
  }
}
