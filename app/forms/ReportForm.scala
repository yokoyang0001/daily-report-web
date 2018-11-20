package forms

import play.api.data.Form
import play.api.data.Forms._

case class ReportForm(content: String, workAt: String)

object ReportForm {
  val form: Form[ReportForm] = Form(
    mapping(
      "content" -> text,
      "workAt" -> text,
    )(ReportForm.apply)(ReportForm.unapply)
  )
}

case class ReportUpdateForm(id: Long, content: String, workAt: String)

object ReportUpdateForm {
  val form: Form[ReportUpdateForm] = Form(
    mapping(
      "id" -> longNumber,
      "content" -> text,
      "workAt" -> text,
    )(ReportUpdateForm.apply)(ReportUpdateForm.unapply)
  )
}

case class ReportDeleteForm(id: Long)

object ReportDeleteForm {
  val form: Form[ReportDeleteForm] = Form(
    mapping(
      "id" -> longNumber
    )(ReportDeleteForm.apply)(ReportDeleteForm.unapply)
  )
}