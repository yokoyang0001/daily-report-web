package controllers

import forms.LoginForm
import javax.inject.Inject
import javax.inject.Singleton
import models.User
import play.api.mvc._
import play.filters.csrf.CSRF

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index() = Action { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    Ok(views.html.login(token.get.value, errorMessage))
  }

  def login() = Action { implicit request =>
    LoginForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val user = User.authenticate(formData.loginId, formData.password)
        if (user != null) {
          Redirect(routes.HomeController.index()).withSession(User.SESSION_USER_ID_KEY -> String.valueOf(user.id))
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Login ID or password is incorrect"
          Ok(views.html.login(token.get.value, errorMessage))
        }
      }
    )
  }

  def logout = Action {
    Redirect(routes.LoginController.index()).withNewSession
  }
}
