package controllers

import auth.SecurityActivator
import javax.inject.{Inject, Singleton}
import models.Project
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, MessagesActionBuilder}
import scala.collection.JavaConverters._

@Singleton
class ProjectController @Inject()(
                               cc: ControllerComponents,
                               auth: SecurityActivator,
                               messagesAction: MessagesActionBuilder
                             )
  extends AbstractController(cc) {

  def index = auth { implicit request =>
    Ok(Json.toJson(Project.find().findList().asScala))
  }

  def getById(id: Int) = auth { implicit request =>
    val project = Project.find(id)
    if (project != null) {
      Ok(Json.toJson(project))
    } else {
      NotFound
    }
  }
}