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
  @BeanProperty
  var status: String = Report.STATUS_DRAFT

  @OneToMany(mappedBy = "report")
  @BeanProperty
  var reviews: java.util.List[ReportReview] = new java.util.ArrayList[ReportReview]

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date
}

object Report extends Dao(classOf[Report]) {
  val STATUS_DRAFT = "draft"
  val STATUS_REQUESTED = "requested"
  val STATUS_APPROVED = "approved"
  val STATUS_REJECTED = "rejected"
}
