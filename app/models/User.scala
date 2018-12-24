package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._
import play.api.libs.json._
import scala.collection.JavaConverters._

@Entity
@Table(name = "users")
class User {

  @Id
  var id = 0L

  @Column(nullable = false, length = 10, unique = true)
  var loginId: String = _

  @Column(nullable = false)
  var passwordHash: String = _

  @Column(nullable = false)
  var isManager: Boolean = false

  @Column(nullable = false, length = 10)
  var name: String = _

  @Column(nullable = false)
  var accessToken: String = _

  @OneToMany(mappedBy = "user")
  var reports: java.util.List[Report] = new java.util.ArrayList[Report]

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date

}

object User extends Dao(classOf[User]){

  implicit object UserFormat extends Format[User] {
    def writes(user: User): JsValue = {
      val userSeq = Seq(
        "id" -> JsNumber(user.id),
        "isManager" -> JsBoolean(user.isManager),
        "name" -> JsString(user.name),
        "accessToken" -> JsString(user.accessToken),
        "reports" -> Json.toJson(user.reports.asScala),
        "createdAt" -> JsString("%tY-%<tm-%<td %<tH:%<tM:%<tS" format user.createdAt),
        "updatedAt" -> JsString("%tY-%<tm-%<td %<tH:%<tM:%<tS" format user.updatedAt)
      )
      JsObject(userSeq)
    }

    override def reads(json: JsValue): JsResult[User] = ???
  }

}