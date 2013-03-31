package parser

import org.specs2.mutable.Specification
import services.Email
import javax.mail.internet.NewsAddress

class EventParserSpec extends Specification {
  "parse from email" should {
    "handle empty email" in {
      val email = Email(new NewsAddress, "the title", "empty body")

      val actualEvent = new EventParser().parseFromEmail(email)

      actualEvent.imageUrl must beNone
      actualEvent.title must_== "the title"
      actualEvent.notes must_== Some("empty body")
      actualEvent.tags must_== None
    }

    "follow links in body" in {
      val email = Email(new NewsAddress, "the title", """<a href="http://www.thrillist.com/eat/san-francisco/the-castro/94103/pica-picas-full-service-restaurant">Link</a>""")

      val parser = new EventParser()
      val actualEvent = parser.parseFromEmail(email)
      val linkEvent = parser.parseFromLink("""http://www.thrillist.com/eat/san-francisco/the-castro/94103/pica-picas-full-service-restaurant""")

      actualEvent.notes must_== linkEvent.notes
    }
  }

}
