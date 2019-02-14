package fusion

import slick.jdbc.JdbcBackend.{ BaseSession, DatabaseDef }

import anorm._
import fusion.models.Author
import slick.jdbc.JdbcBackend

class AnormRepo(implicit db: JdbcBackend.Database) {

  val connection                              = new UnmanagedSession(db)
  private val authorParser: RowParser[Author] = Macro.namedParser[Author]

}

class UnmanagedSession(database: DatabaseDef) extends BaseSession(database) {
  override def close() = ()
}
