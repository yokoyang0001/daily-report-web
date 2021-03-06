package models

import dao.Dao
import javax.persistence._
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
}

object User extends Dao(classOf[User]) {
  def apply(loginId: String, password: String, name: String): User = {
    val user = new User()
    user.setLoginId(loginId)
    user.setPassword(password)
    user.setName(name)
    user
  }

  def unapply(user: User): Option[(String, String, String)] = Some((user.getLoginId, user.getPassword, user.getName))

  def authenticate(username: String, password: String): User =
    find().where().eq("username", username).eq("password", password).findOne()
}
