package controllers

import forms.LoginForm
import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc._

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login(LoginForm.form))
  }

  def login() = Action { implicit request =>
    LoginForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.login(formWithErrors))
      },
      formData => {
        Ok(views.html.index("Login success"))
      }
    )
  }
}
