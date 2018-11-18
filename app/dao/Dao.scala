package dao

import io.ebean.{Ebean, Query}

abstract class Dao[T](cls: Class[T]) {

  def find(id: Any): T = Ebean.find(cls, id)

  def find(): Query[T] = Ebean.find(cls)

  def save(o: Any): Unit = Ebean.save(o)

  def delete(o: Any): Unit = Ebean.delete(o)
}