package forms

import play.api.data.Form
import play.api.data.Forms._

case class ProjectForm(name: String, content: String)

object ProjectForm {
  val form: Form[ProjectForm] = Form(
    mapping(
      "name" -> nonEmptyText(maxLength = 20),
      "content" -> nonEmptyText(maxLength = 1000),
    )(ProjectForm.apply)(ProjectForm.unapply)
  )
}

case class ProjectUpdateForm(id: Long, name: String, content: String)

object ProjectUpdateForm {
  val form: Form[ProjectUpdateForm] = Form(
    mapping(
      "id" -> longNumber(min = 1),
      "name" -> nonEmptyText(maxLength = 20),
      "content" -> nonEmptyText(maxLength = 1000),
    )(ProjectUpdateForm.apply)(ProjectUpdateForm.unapply)
  )
}

case class ProjectDeleteForm(id: Long)

object ProjectDeleteForm {
  val form: Form[ProjectDeleteForm] = Form(
    mapping(
      "id" -> longNumber(min = 1)
    )(ProjectDeleteForm.apply)(ProjectDeleteForm.unapply)
  )
}

case class ProjectUserAssignForm(projectId: Long, userId: Long)

object ProjectUserAssignForm {
  val form: Form[ProjectUserAssignForm] = Form(
    mapping(
      "projectId" -> longNumber(min = 1),
      "userId" -> longNumber(min = 1),
    )(ProjectUserAssignForm.apply)(ProjectUserAssignForm.unapply)
  )
}

case class ProjectUserWithDrawForm(id: Long)

object ProjectUserWithDrawForm {
  val form: Form[ProjectUserWithDrawForm] = Form(
    mapping(
      "id" -> longNumber(min = 1),
    )(ProjectUserWithDrawForm.apply)(ProjectUserWithDrawForm.unapply)
  )
}