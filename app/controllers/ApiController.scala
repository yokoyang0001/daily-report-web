package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.DataRepository

@Singleton
class ApiController @Inject()(
                               cc: ControllerComponents,
                               dataRepository: DataRepository
                             )
  extends AbstractController(cc) {

  def ping = Action { implicit request =>
    Ok("Hello, Scala!")
  }

  def getUser(userId: Int) = Action { implicit request =>
    dataRepository.getUser(userId) map { user =>
      // If the post was found, return a 200 with the post data as JSON
      Ok(Json.toJson(user))
    } getOrElse NotFound    // otherwise, return Not Found
  }


}