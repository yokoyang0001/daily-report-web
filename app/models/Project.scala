package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._
import play.api.libs.json._

@Entity
@Table(name = "projects")
class Project {

  @Id
  var id = 0L

  @Column(nullable = false, length = 10, unique = true)
  var code: String = _

  @Column(nullable = false)
  var name: String = _

  @Column(nullable = false)
  var status: String = _

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date

}

object Project extends Dao(classOf[Project]){

  implicit object ProjectFormat extends Format[Project] {
    def writes(project: Project): JsValue = {
      val projectSeq = Seq(
        "id" -> JsNumber(project.id),
        "code" -> JsString(project.code),
        "name" -> JsString(project.name),
        "status" -> JsString(project.status),
        "createdAt" -> JsString("%tY-%<tm-%<td %<tH:%<tM:%<tS" format project.createdAt),
        "updatedAt" -> JsString("%tY-%<tm-%<td %<tH:%<tM:%<tS" format project.updatedAt)
      )
      JsObject(projectSeq)
    }

    override def reads(json: JsValue): JsResult[Project] = ???
  }

}