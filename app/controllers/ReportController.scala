package controllers

import auth.SecurityActivator
import javax.inject.{Inject, Singleton}
import models.{Report, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, MessagesActionBuilder}

import scala.collection.JavaConverters._

@Singleton
class ReportController @Inject()(
                               cc: ControllerComponents,
                               auth: SecurityActivator,
                               messagesAction: MessagesActionBuilder
                             )
  extends AbstractController(cc) {

  def index = auth { implicit request =>
    Ok(Json.toJson(Report.find().findList().asScala))
  }

  def getById(id: Int) = auth { implicit request =>
    val project = Report.find(id)
    if (project != null) {
      Ok(Json.toJson(project))
    } else {
      NotFound
    }
  }

  case class ReportForm(userId: Long, title: String, content: String)
  object ReportForm {
    val form: Form[ReportForm] = Form(
      mapping(
        "userId" -> longNumber,
        "title" -> text,
        "content" -> text
      )(ReportForm.apply)(ReportForm.unapply)
    )
  }

  def postReport = auth { implicit request =>
    request.body.asJson.map(
      json =>
        ReportForm.form.bind(json).fold(
          errors =>
            BadRequest,
          form => {
            val user = User.find(form.userId)
            user match {
              case null => BadRequest
              case _ => {
                val report = new Report
                report.title = form.title
                report.content = form.content
                report.status = Report.STATUS_PROGRESS
                Report.save(report)
                Ok(Json.toJson(report))
              }
            }
          }
        )
    )
      .getOrElse(
        BadRequest
      )
  }

}