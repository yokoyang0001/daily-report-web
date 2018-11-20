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

