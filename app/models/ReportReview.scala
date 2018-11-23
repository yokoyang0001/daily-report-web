package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "report_reviews")
class ReportReview {
  @Id
  val id: Long = 0

  @ManyToOne
  @JoinColumn(name = "report_id")
  @BeanProperty
  var report: Report = _

  @ManyToOne
  @JoinColumn(name = "user_id")
  @BeanProperty
  var user: User = _

  @Column(nullable = true)
  @BeanProperty
  var comment: String = _

  @Column(nullable = false)
  @BeanProperty
  var action: Int = _

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt: Date = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date
}

object ReportReview extends Dao(classOf[ReportReview]) {
  val CATEGORY_APPROVE: Int = 0
  val CATEGORY_REJECT: Int = 1
}

