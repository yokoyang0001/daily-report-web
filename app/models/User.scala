package models

import java.util._

import dao.Dao
import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._
import org.mindrot.jbcrypt.BCrypt

import scala.beans.BeanProperty

@Entity
@Table(name = "users")
class User {
  @Id
  val id: Long = 0

  @Column(nullable = false)
  @BeanProperty
  var loginId: String = _

  @Column(nullable = false)
  @BeanProperty
  var password: String = _

  @Column(nullable = false)
  @BeanProperty
  var name: String = _

  @Column(nullable = false)
  @BeanProperty
  var role: Int = _

  @Column
  @BeanProperty
  var lastLoginAt = new Date

  @Column(nullable = false)
  @CreatedTimestamp
  var createdAt = new Date

  @Column(nullable = true)
  @UpdatedTimestamp
  var updatedAt = new Date
}

object User extends Dao(classOf[User]) {
  val SESSION_USER_ID_KEY: String = "userId"

  val ROLE_ADMIN: Int = 0
  val ROLE_READER: Int = 1
  val ROLE_MEMBER: Int = 2

  def authenticate(loginId: String, password: String): User = {
    val user = find().where().eq("loginId", loginId).findOne()
    if (user != null && BCrypt.checkpw(password, user.password)) {
      return user
    }
    null
  }
}
