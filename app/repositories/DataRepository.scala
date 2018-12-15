package repositories

import models.User


class DataRepository {
  private val users = Seq(
    User(1, "Taro Tanaka"),
    User(2, "Jito Sato"),
  )

  def getUser(userId: Int): Option[User] = users.collectFirst {
    case u if u.id == userId => u
  }
}
