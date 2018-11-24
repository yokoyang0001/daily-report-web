package forms

import play.api.data.Form
import play.api.data.Forms._

case class ReportForm(content: String, workAt: String)

object ReportForm {
  val form: Form[ReportForm] = Form(
    mapping(
      "content" -> nonEmptyText(maxLength = 1000),
      "workAt" -> nonEmptyText,
    )(ReportForm.apply)(ReportForm.unapply)
  )
}

case class ReportUpdateForm(id: Long, content: String, workAt: String)

object ReportUpdateForm {
  val form: Form[ReportUpdateForm] = Form(
    mapping(
      "id" -> longNumber(min = 1),
      "content" -> nonEmptyText(maxLength = 1000),
      "workAt" -> text,
    )(ReportUpdateForm.apply)(ReportUpdateForm.unapply)
  )
}

case class ReportDeleteForm(id: Long)

object ReportDeleteForm {
  val form: Form[ReportDeleteForm] = Form(
    mapping(
      "id" -> longNumber(min = 1)
    )(ReportDeleteForm.apply)(ReportDeleteForm.unapply)
  )
}

case class ReportReviewForm(reportId: Long, comment: String, action: Int)

object ReportReviewForm {
  val form: Form[ReportReviewForm] = Form(
    mapping(
      "reportId" -> longNumber(min = 1),
      "comment" -> text,
      "action" -> number(min = 0, max = 1)
    )(ReportReviewForm.apply)(ReportReviewForm.unapply)
  )
}