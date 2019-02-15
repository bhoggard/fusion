package example

import com.zaxxer.hikari.{ HikariConfig, HikariDataSource }
import fusion.AnormRepo
import fusion.models.Author
import org.flywaydb.core.Flyway
import org.flywaydb.core.internal.database.base.Database
import org.scalatest._
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database

class AnormRepoSpec extends FlatSpec with Matchers {

  val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl("jdbc:postgresql://localhost/fusion_test")
  hikariConfig.setUsername("postgres")
  hikariConfig.setPassword("")

  val dataSource: HikariDataSource = new HikariDataSource(hikariConfig)

//  implicit val profile = org.postgresql.ds.PGSimpleDataSource

  implicit val db: JdbcBackend.DatabaseDef =
    slick.jdbc.JdbcBackend.Database.forDataSource(dataSource, Some(10))

  val flyway = new Flyway()
  flyway.setDataSource(dataSource)
  flyway.clean()
  flyway.migrate()

  lazy val repo = new AnormRepo()

  "The repo" should "return Some(Author) for an existing ID" in {
    val authorOpt = repo.loadAuthor(1)
    authorOpt shouldEqual Some(Author(1, "N.K. Jemisin"))
  }

  "The repo" should "return None for a non-existent ID" in {
    repo.loadAuthor(100) shouldEqual None
  }

}
