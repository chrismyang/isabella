package services

import javax.mail.{MessagingException, NoSuchProviderException, Folder, Session}

class EmailReceiver {

  def doSomething() {
    val props = System.getProperties
    props.setProperty("mail.store.protocol", "imaps")
    val session = Session.getDefaultInstance(props, null)
    val store = session.getStore("imaps")
    try {
      // use imap.gmail.com for gmail
      store.connect("imap.gmail.com", "isabellas.got.it@gmail.com", "socialsecretary")
      val inbox = store.getFolder("Inbox")
      inbox.open(Folder.READ_ONLY)

      // limit this to 20 message during testing
      val messages = inbox.getMessages()
      val limit = 20
      var count = 0
      for (message <- messages) {
        count = count + 1
        if (count > limit) System.exit(0)
        println(message.getSubject())
      }
      inbox.close(true)
    } catch {
      case e: NoSuchProviderException =>  e.printStackTrace()
      case me: MessagingException =>      me.printStackTrace()
    } finally {
      store.close()
    }
  }
}
