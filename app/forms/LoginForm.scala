package forms

import play.api.data.Form
import play.api.data.Forms._

case class LoginForm(loginId: String, password: String)

object LoginForm {
  val form: Form[LoginForm] = Form(
    mapping(
      "loginId" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )
}

