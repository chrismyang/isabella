package parser

import models.{Location, Event}
import services.Email
import org.jsoup.Jsoup

class EventParser {

  def parseFromEmail(email: Email): Event = {
    val title = email.subject
    val notes = Some(email.body)

    Event(None, title, None, None, notes, Location("foo", null))
  }

  def parseFromLink(link: String): Event = {
    Event(None, "", None, None, None, Location("", null))
  }

  def parseFromWebPage(htmlContent: String, url: String): Event = {
    val document = Jsoup.parse(htmlContent, url)

    if (isThrillistLink(url)) {
      val title = document.select("[name=twitter:title]").attr("content")

      val imageUrl = Some(document.select(".tlc-slide-media img").attr("src"))

      val notes = Some(document.select("[name=twitter:description]").attr("content"))

      val address = {
        val streetAddress = document.select(".item-address1").text()
        val otherPart = document.select(".item-address2").text()
        streetAddress + ", " + otherPart
      }

      Event(None, title, imageUrl, None, notes, Location(address, null))
    } else {

      val imageUrl = None
      val notes = None
      val address = ""

      Event(None, "", imageUrl, None, notes, Location(address, null))
    }
  }

  private def isThrillistLink(url: String) = url.contains("""thrillist.com""")
}
