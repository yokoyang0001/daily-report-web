package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "reports")
class Report {
  @Id
  val id: Long = 0

  @ManyToOne
  @JoinColumn(name = "user_id")
  @BeanProperty
  var user: User = _

  @Column(nullable = false)
  @BeanProperty
  var content: String = _

  @Column(nullable = false)
  @BeanProperty
  var workAt = new Date

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date
}

object Report extends Dao(classOf[Report]) {
  def apply(
             user: User,
             content: String,
             workAt: Date,
           ): Report = {
    val report = new Report()
    report.setUser(user)
    report.setContent(content)
    report.setWorkAt(workAt)
    report
  }

  def unapply(report: Report): Option[(User, String, Date)] = Some((report.getUser, report.getContent, report.getWorkAt))
}
