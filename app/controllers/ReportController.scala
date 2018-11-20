package controllers

import javax.inject.{Inject, Singleton}
import models.Report
import play.api.mvc._

@Singleton
class ReportController @Inject()(
                                cc: ControllerComponents,
                                authenticatedController: AuthenticatedController
                              ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = authenticatedController { implicit request: Request[AnyContent] =>
    val reports = Report.find().findList()
    Ok(views.html.reports(reports))
  }
}
