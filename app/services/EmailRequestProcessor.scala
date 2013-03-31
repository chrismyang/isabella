package services

import akka.actor.{Props, Actor}
import parser.EventParser
import models.Event
import play.api.libs.concurrent.Akka
import java.util.concurrent.TimeUnit
import akka.util.Duration
import play.api.{Play, Logger}
import Play.current

class EmailRequestProcessor(emailReceiver: EmailReceiver, eventManager: EventManager) extends Actor {
  import EmailRequestProcessor._

  protected def receive = {
    case Fetch =>
      val emailsToProcess = emailReceiver.fetchNewMessages()

      for (email <- emailsToProcess) {
        val promise = new EventParser().parseFromEmail(email)

        promise onRedeem { event =>
          self ! event
        }
      }

    case event: Event => eventManager.create(event)
  }
}

object EmailRequestProcessor {
  case object Fetch

  def start() {
    val frequency = Duration(1, TimeUnit.MINUTES)

    Logger.info("Starting email monitoring at frequency " + frequency)

    val processor = Akka.system.actorOf(Props(new EmailRequestProcessor(new EmailReceiver, EventManagerService)), "email-request-processor")
    Akka.system.scheduler.schedule(Duration.Zero, frequency, processor, Fetch)
  }
}
