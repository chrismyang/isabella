package parser

import org.specs2.mutable.Specification
import services.Email
import javax.mail.internet.NewsAddress
import org.apache.commons.io.FileUtils
import java.io.File

class EventParserSpec extends Specification {
  "parse from email" should {
    "handle empty email" in {
      val actualEvent = {
        val email = Email(new NewsAddress, "the title", "empty body")
        new EventParser().parseFromEmail(email).await.get
      }

      actualEvent.imageUrl must beNone
      actualEvent.title must_== "the title"
      actualEvent.notes must_== Some("empty body")
      actualEvent.tags must_== None
    }

//    "follow links in body" in {
//      val email = Email(new NewsAddress, "the title", """<a href="http://www.thrillist.com/eat/san-francisco/the-castro/94103/pica-picas-full-service-restaurant">Link</a>""")
//
//      val parser = new EventParser()
//      val actualEvent = parser.parseFromEmail(email)
//      val linkEvent = parser.parseFromLink("""http://www.thrillist.com/eat/san-francisco/the-castro/94103/pica-picas-full-service-restaurant""")
//
//      actualEvent.notes must_== linkEvent.notes
//    }
  }

  "parse web page" should {
    "handle thrillist" in {
      val actualEvent = {
        val html = FileUtils.readFileToString(new File("test/thrillist.html"))
        val url = """http://www.thrillist.com/eat/san-francisco/the-castro/94103/pica-picas-full-service-restaurant"""
        new EventParser().parseFromWebPage(html, url).await.get
      }

      actualEvent.imageUrl must_== Some("""http://assets3.thrillist.com/v1/image/718806/size/tl-horizontal_main""")
      actualEvent.title must_== "Pica Pica Castro"
      actualEvent.location.text must_== "3970 17th St, San Francisco, CA 94103"
    }

    "handle sosh" in {
      val actualEvent = {
        val html = FileUtils.readFileToString(new File("test/sosh.html"))
        val url = """http://sosh.com/inverness-ca/tomales-bay-state-park/hike-tomales-bay-woodland-hike/a/ShaE/?ref=weekender_button"""
        new EventParser().parseFromWebPage(html, url).await.get
      }

      actualEvent.imageUrl must_== Some("""http://s.bso.sh/thumbs/f9/37/f937cae957a71dc8a742e584ea1e4134.jpg""")
      actualEvent.title must_== "Hike: Tomales Bay Hike"
      actualEvent.location.text must_== "1208 Pierce Point Rd, Inverness, CA 94937"
    }

    "handle random site that hs og data" in {
      val actualEvent = {
        val html = FileUtils.readFileToString(new File("test/randomOg.html"))
        val url = """http://www.ricepaperscissors.com/#_"""
        new EventParser().parseFromWebPage(html, url).await.get
      }

      actualEvent.imageUrl must_== Some("""http://ec2-107-20-93-72.compute-1.amazonaws.com/?capture=1024x768&image=480x360&url=http%3A//www.ricepaperscissors.com""")
      actualEvent.title must_== "Rice Paper Scissors"
      actualEvent.notes must_== Some("""Weekly pop-up at Mojo Bicycle Cafe Thursdays | 6-10PM 639 Divisadero, San Francisco CA Menu for Thursday, March 28th Sign up for the email list to hear about our monthly events.""")

    }
  }

}
