package controllers

import javax.inject.Inject
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AuthenticatedController @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    val user = request.session.get(models.User.SESSION_USER_ID_KEY)
    user match {
      case None => {
        Future.successful(Redirect(routes.LoginController.index()))
      }
      case Some(u) => {
        val res: Future[Result] = block(request)
        res
      }
    }
  }
}