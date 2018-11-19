package controllers

import javax.inject.Inject
import javax.inject.Singleton
import models.User
import play.api.mvc._

@Singleton
class HomeController @Inject()(
                                cc: ControllerComponents,
                                authenticatedController: AuthenticatedController
                              ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = authenticatedController { implicit request: Request[AnyContent] =>
    val userId = request.session.get(models.User.SESSION_USER_ID_KEY).get
    val user = User.find(userId)
    Ok(views.html.index("Welcome back!! " + user.name))
  }
}
