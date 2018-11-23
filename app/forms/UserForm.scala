package forms

import play.api.data.Form
import play.api.data.Forms._

case class UserForm(loginId: String, password: String, passwordConfirm: String, name: String, role: Int)

object UserForm {
  val form: Form[UserForm] = Form(
    mapping(
      "loginId" -> text,
      "password" -> text,
      "passwordConfirm" -> text,
      "name" -> text,
      "role" -> number
    )(UserForm.apply)(UserForm.unapply)
  )
}

case class UserUpdateForm(id: Long, name: String, role: Int)

object UserUpdateForm {
  val form: Form[UserUpdateForm] = Form(
    mapping(
      "id" -> longNumber,
      "name" -> text,
      "role" -> number
    )(UserUpdateForm.apply)(UserUpdateForm.unapply)
  )
}

case class UserDeleteForm(id: Long)

object UserDeleteForm {
  val form: Form[UserDeleteForm] = Form(
    mapping(
      "id" -> longNumber
    )(UserDeleteForm.apply)(UserDeleteForm.unapply)
  )
}