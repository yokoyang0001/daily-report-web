package models

import dao.Dao
import play.api.libs.json.Json

case class User(id: Int, name: String)

object User extends Dao(classOf[User]) {
  val SESSION_USER_ID_KEY: String = "userId"

  val ROLE_ADMIN: Int = 0
  val ROLE_READER: Int = 1
  val ROLE_MEMBER: Int = 2

  implicit val format = Json.format[User]
}
