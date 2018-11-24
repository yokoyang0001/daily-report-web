package forms

import play.api.data.Form
import play.api.data.Forms._

case class UserForm(loginId: String, password: String, passwordConfirm: String, name: String, role: Int)

object UserForm {
  val form: Form[UserForm] = Form(
    mapping(
      "loginId" -> nonEmptyText(minLength = 8, maxLength = 255),
      "password" -> nonEmptyText(minLength = 8, maxLength = 255),
      "passwordConfirm" -> text,
      "name" -> nonEmptyText(maxLength = 10),
      "role" -> number(0, 2)
    )(UserForm.apply)(UserForm.unapply)
  )
}

case class UserUpdateForm(id: Long, name: String, role: Int)

object UserUpdateForm {
  val form: Form[UserUpdateForm] = Form(
    mapping(
      "id" -> longNumber(1),
      "name" -> nonEmptyText(maxLength = 10),
      "role" -> number(0, 2)
    )(UserUpdateForm.apply)(UserUpdateForm.unapply)
  )
}

case class UserDeleteForm(id: Long)

object UserDeleteForm {
  val form: Form[UserDeleteForm] = Form(
    mapping(
      "id" -> longNumber(1)
    )(UserDeleteForm.apply)(UserDeleteForm.unapply)
  )
}