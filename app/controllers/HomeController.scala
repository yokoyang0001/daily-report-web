package controllers

import auth.SecurityActivator
import javax.inject.{Inject, Singleton}
import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, MessagesActionBuilder}

@Singleton
class HomeController @Inject()(
                               cc: ControllerComponents,
                               auth: SecurityActivator,
                               messagesAction: MessagesActionBuilder
                             )
  extends AbstractController(cc) {

  def index = auth { implicit request =>
    System.out.print(Json.obj("aaa" -> request.headers.get("authorization")))
    val user = User.find(1)
    Ok(Json.toJson(user))
  }

  case class LoginForm(loginId: String, password: String)
  object LoginForm {
    val form: Form[LoginForm] = Form(
      mapping(
        "loginId" -> text,
        "password" -> text
      )(LoginForm.apply)(LoginForm.unapply)
    )
  }

  def login = messagesAction { implicit request =>
      request.body.asJson.map(
      json =>
        LoginForm.form.bind(json).fold(
          errors =>
            Ok(errors.errorsAsJson),
          form => {
            val user = User.find().where().eq("login_id", form.loginId).findOne()
            if (user != null) {
              Ok(Json.obj("token" -> user.accessToken))
            } else {
              NotFound
            }
          }
        )
    )
      .getOrElse(
        BadRequest
      )
  }

}