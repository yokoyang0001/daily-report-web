package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "project_users")
class ProjectUser {
  @Id
  var id: Long = _

  @JoinColumn(name = "user_id")
  @ManyToOne
  @BeanProperty
  var user: User = _

  @JoinColumn(name="project_id")
  @ManyToOne
  @BeanProperty
  var project: Project = _

  @Column(nullable = false, length = 100)
  @BeanProperty
  var role: Int = _

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = false)
  @UpdatedTimestamp
  var updatedAt = new Date
}

object ProjectUser extends Dao(classOf[ProjectUser]) {}