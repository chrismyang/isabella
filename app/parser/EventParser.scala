package parser

import models.{Location, Event}
import services.Email
import org.jsoup.Jsoup
import play.api.libs.ws.WS
import play.api.libs.concurrent.Promise

class EventParser {

  def parseFromEmail(email: Email): Promise[Event] = {
    hasLink(email.body) match {
      case Some(link) => parseFromLink(link)
      case None =>
        val title = email.subject
        val notes = Some(email.body)

        val event = Event(None, title, None, None, notes, Location("foo", null))
        Promise.pure(event)
    }
  }

  private def hasLink(htmlContent: String) = {
    val document = Jsoup.parse(htmlContent)
    val elements = document.select("a[href]")

    if (elements.size == 0) {
      None
    } else {
      Some(elements.get(0).attr("href"))
    }
  }

  def parseFromLink(link: String): Promise[Event] = {
    WS.url(link).get() flatMap { response =>
      this.parseFromWebPage(response.body, response.getAHCResponse.getUri.toString)
    }
  }

  def parseFromWebPage(htmlContent: String, url: String): Promise[Event] = {
    val document = Jsoup.parse(htmlContent, url)

    val result = if (isThrillistLink(url)) {
      val title = document.select("[name=twitter:title]").attr("content")

      val imageUrl = Some(document.select(".tlc-slide-media img").attr("src"))

      val notes = Some(document.select("[name=twitter:description]").attr("content"))

      val address = {
        val streetAddress = document.select(".item-address1").text()
        val otherPart = document.select(".item-address2").text()
        streetAddress + ", " + otherPart
      }

      Event(None, title, imageUrl, None, notes, Location(address, null))
    } else if (isSoshLink(url)) {
      val title = document.select("[property=og:title]").attr("content")

      val imageUrl = Some(document.select("[property=og:image]").attr("content"))

      val notes = Some(document.select("[property=og:description]").attr("content"))

      val address = {
        val streetAddress = document.select("#google-map-address-overlay").html()
        streetAddress.split("<br />").map(_.trim).mkString(", ")
      }

      Event(None, title, imageUrl, None, notes, Location(address, null))
    } else {
      val title = {
        val ogTitle = document.select("[property=og:title]")

        if (!ogTitle.isEmpty) {
          ogTitle.attr("content")
        } else {
          document.select("title").text()
        }
      }

      val imageUrl = {
        val ogImage = document.select("[property=og:image]")

        if (!ogImage.isEmpty) {
          Some(ogImage.attr("content"))
        } else {
          None
        }
      }

      val notes = {
        val ogDescription = document.select("[property=og:description]")

        if (!ogDescription.isEmpty) {
          Some(ogDescription.attr("content"))
        } else {
          None
        }
      }

      val address = ""

      Event(None, title, imageUrl, None, notes, Location(address, null))
    }

    Promise.pure(result)
  }

  private def isThrillistLink(url: String) = url.contains("""thrillist.com""")

  private def isSoshLink(url: String) = url.contains("""sosh.com""")
}
