package services

import javax.mail._
import javax.mail.Flags.Flag
import java.util.regex.Pattern

class EmailReceiver {

  def fetchNewMessages(deleteAfterReading: Boolean = true): Seq[Email] = {
    val session = makeSession

    withStore(session) { store =>
      val inbox = store.getFolder("Inbox")
      inbox.open(Folder.READ_WRITE)

      val allMessages = inbox.getMessages.toSeq

      val emails = allMessages map convertMessageToEmail

      if (deleteAfterReading) {
        makeMessagesAsDeleted(allMessages)
      }

      inbox.close(true)

      emails
    }
  }

  private def makeSession = {
    val props = System.getProperties
    props.setProperty("mail.store.protocol", "imaps")
    val session = Session.getDefaultInstance(props, null)
    session
  }

  private def withStore[A](session: Session)(f: Store => A): A = {
    val store = session.getStore("imaps")
    try {
      // use imap.gmail.com for gmail
      store.connect("imap.gmail.com", "isabellas.got.it@gmail.com", "socialsecretary")

      f(store)

    } finally {
      store.close()
    }
  }

  private def convertMessageToEmail(message: Message): Email = {
    val from = message.getFrom.head
    val subject = message.getSubject

    val content = parseBodyContent(message)

    Email(from, subject, content)
  }

  private def makeMessagesAsDeleted(messages: Seq[Message]) {
    for (message <- messages) {
      message.setFlags(new Flags(Flag.DELETED), true)
    }
  }

  private def parseBodyContent(message: Message): String = {
    message.getContent match {
      case mp: Multipart =>

        val parts = for {
          i <- 0 until mp.getCount
          bp = mp.getBodyPart(i) if isHtml(bp)
        } yield {
          bp.getContent.asInstanceOf[String]
        }

        parts.mkString("")

      case unknownContent =>
        "Sorry we couldn't recognize the body of your email, but here's the best we could do:" + unknownContent.toString
    }
  }

  private def isHtml(bodyPart: BodyPart): Boolean = {
    // A little staggered why this is a regex and not a more simple String equality check, but who knows?
    // http://stackoverflow.com/questions/7212534/read-html-body-of-an-email-using-javamail
    val BodyPartIsHtmlRegEx = Pattern.compile(Pattern.quote("text/html"), Pattern.CASE_INSENSITIVE)
    BodyPartIsHtmlRegEx.matcher(bodyPart.getContentType).find()
  }
}

case class Email(from: Address, subject: String, body: String)
