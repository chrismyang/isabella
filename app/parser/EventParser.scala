package parser

import models.{Location, Event}
import services.Email

class EventParser {

  def parseFromEmail(email: Email): Event = {



    Event(None, "", None, None, None, Location("foo", null))
  }

  def parseFromLink(link: String): Event = {
    null
  }
}
