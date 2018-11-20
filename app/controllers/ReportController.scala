package controllers

import java.text.SimpleDateFormat
import forms.{ReportForm, ReportUpdateForm}
import javax.inject.{Inject, Singleton}
import models.{Report, User}
import play.api.mvc._
import play.filters.csrf.CSRF

@Singleton
class ReportController @Inject()(
                                cc: ControllerComponents,
                                authenticatedController: AuthenticatedController,
                              ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = authenticatedController { implicit request: Request[AnyContent] =>
    val reports = Report.find().findList()
    Ok(views.html.reports(reports))
  }

  def detail(id: Long) = authenticatedController { implicit request: Request[AnyContent] =>
    val report = Report.find(id)
    Ok(views.html.report_detail(report))
  }

  def create() = authenticatedController { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    Ok(views.html.report_create(token.get.value, errorMessage))
  }

  def register() = authenticatedController { implicit request =>
    ReportForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val user = User.find(request.session.get(models.User.SESSION_USER_ID_KEY).get)
        val format = new SimpleDateFormat("yyyy-MM-dd H:m")
        val report = new Report
        report.user = user
        report.content = formData.content
        report.workAt = format.parse(formData.workAt + " 00:00")
        Report.save(report)
        val reports = Report.find().findList()
        if (reports != null) {
          Redirect(routes.ReportController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Login ID or password is incorrect"
          Ok(views.html.report_create(token.get.value, errorMessage))
        }
      }
    )
  }

  def edit(id: Long) = authenticatedController { implicit request =>
    val token: Option[CSRF.Token] = CSRF.getToken
    val errorMessage: String = ""
    val report = Report.find(id)
    Ok(views.html.report_edit(report, token.get.value, errorMessage))
  }

  def update() = authenticatedController { implicit request =>
    ReportUpdateForm.form.bindFromRequest.fold(
      formWithErrors => {
        val token: Option[CSRF.Token] = CSRF.getToken
        val errorMessage: String = "Login ID or password is incorrect"
        BadRequest(views.html.login(token.get.value, errorMessage))
      },
      formData => {
        val user = User.find(request.session.get(models.User.SESSION_USER_ID_KEY).get)
        val format = new SimpleDateFormat("yyyy-MM-dd H:m")
        val report = Report.find(formData.id)
        report.user = user
        report.content = formData.content
        report.workAt = format.parse(formData.workAt + " 00:00")
        Report.save(report)
        val reports = Report.find().findList()
        if (reports != null) {
          Redirect(routes.ReportController.index())
        } else {
          val token: Option[CSRF.Token] = CSRF.getToken
          val errorMessage: String = "Login ID or password is incorrect"
          Ok(views.html.report_create(token.get.value, errorMessage))
        }
      }
    )
  }
}
