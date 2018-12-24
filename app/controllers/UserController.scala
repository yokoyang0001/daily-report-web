package controllers

import auth.SecurityActivator
import javax.inject.{Inject, Singleton}
import models.User
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, MessagesActionBuilder}
import scala.collection.JavaConverters._

@Singleton
class UserController @Inject()(
                               cc: ControllerComponents,
                               auth: SecurityActivator,
                               messagesAction: MessagesActionBuilder
                             )
  extends AbstractController(cc) {

  def index = auth { implicit request =>
    Ok(Json.toJson(User.find().findList().asScala))
  }

  def getById(id: Int) = auth { implicit request =>
    val user = User.find(id)
    if (user != null) {
      Ok(Json.toJson(user))
    } else {
      NotFound
    }
  }
}