package controllers

import javax.inject.{Inject, Singleton}
import models.User
import play.api.mvc._

@Singleton
class UserController @Inject()(
                                cc: ControllerComponents,
                                authenticatedController: AuthenticatedController
                              ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = authenticatedController { implicit request: Request[AnyContent] =>
    val users = User.find().findList()
    Ok(views.html.users(users))
  }
}
