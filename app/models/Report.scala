package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._
import play.api.libs.json._


@Entity
@Table(name = "reports")
class Report {

  @Id
  var id = 0L

  @ManyToOne
  @JoinColumn(name = "user_id")
  var user: User = _

  @Column(nullable = false, length = 10, unique = true)
  var title: String = _

  @Column(nullable = false)
  var content: String = _

  @Column(nullable = false)
  var status: String = _

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date

}

object Report extends Dao(classOf[Report]){

  val STATUS_PROGRESS = "progress"
  implicit object ReportFormat extends Format[Report] {
    def writes(report: Report): JsValue = {
      val projectSeq = Seq(
        "id" -> JsNumber(report.id),
        "title" -> JsString(report.title),
        "content" -> JsString(report.content),
        "status" -> JsString(report.status),
        "createdAt" -> JsString("%tY-%<tm-%<td %<tH:%<tM:%<tS" format report.createdAt),
        "updatedAt" -> JsString("%tY-%<tm-%<td %<tH:%<tM:%<tS" format report.updatedAt)
      )
      JsObject(projectSeq)
    }

    override def reads(json: JsValue): JsResult[Report] = ???
  }

}