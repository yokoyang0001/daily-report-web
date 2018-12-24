package auth

import javax.inject.Inject
import models.User
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class SecurityActivator @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    val authToken = request.headers.get("authorization")
    authToken match {
      case None => {
        Future.successful(Results.Unauthorized)
      }
      case Some(_) => {
        val token = authToken.get.toString.replace("Bearer ", "")
        val user = User.find().where().eq("access_token", token).findOne()
        if (user != null) {
          block(request)
        } else {
          Future.successful(Results.NotFound)
        }
      }
    }
  }
}