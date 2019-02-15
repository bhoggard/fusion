package fusion

import java.sql.Connection

import slick.jdbc.JdbcBackend.{ BaseSession, DatabaseDef }
import anorm._
import fusion.models.Author
import slick.jdbc.JdbcBackend

class AnormRepo(implicit db: JdbcBackend.Database) {

  def loadAuthor(id: Long): Option[Author] = withConnection { implicit c =>
    SQL("SELECT * FROM authors WHERE id = {id}")
      .on("id" -> id)
      .as(authorParser.singleOpt)
  }

  private val authorParser: RowParser[Author] = Macro.namedParser[Author]

  private def getConnection(autocommit: Boolean = true): Connection = {
    val connection = db.source.createConnection()
    connection.setAutoCommit(autocommit)
    connection
  }

  private def withConnection[A](block: Connection ⇒ A): A = {
    val connection = getConnection()
    println(connection.getCatalog)
    try {
      block(connection)
    } finally {
      connection.close()
    }
  }

}

class UnmanagedSession(database: DatabaseDef) extends BaseSession(database) {
  override def close() = ()
}
