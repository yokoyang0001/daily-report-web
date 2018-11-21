package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "projects")
class Project {
  @Id
  val id: Long = 0

  @Column(nullable = false, length = 20)
  @BeanProperty
  var name: String = _

  @Column(nullable = false, length = 100)
  @BeanProperty
  var content: String = _

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date
}

object Project extends Dao(classOf[Project]) {}