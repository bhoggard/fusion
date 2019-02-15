package fusion

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database

import scala.io.StdIn

object FusionServer {
  implicit val db: JdbcBackend.Database = Database.forConfig("slick.db")
  lazy val repo                         = new AnormRepo()

  def main(args: Array[String]) {

    implicit val system       = ActorSystem("fusion")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      path("hello") {
        get {
          complete(
            HttpEntity(ContentTypes.`text/html(UTF-8)`,
                       "<h1>Say hello to akka-http</h1>")
          )
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
