package rws.quant.tdameritrade.timeseries

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class Query extends Actor with ActorLogging {

  implicit val actorSystem: ActorSystem = ActorSystem("TDAmeritradeQuery")
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  import Query._

  override def receive: Receive = {
    case r: Request => {
      val request = HttpRequest(
        HttpMethods.GET,
        Uri("https://api.tdameritrade.com").withQuery(r.request.getQuery()),
        r.request.getHeaders(),
        HttpEntity.Empty,
        HttpProtocols.`HTTP/1.1`
      )

      val response: Future[HttpResponse] = Http().singleRequest(request)

      response.onComplete { x =>
        sender() ! Response(r, x)
      }
    }
    case _ =>
  }
}

object Query {

  case class Request(sender: ActorRef, request: TimeSeries.Request)

  case class Response(request: Query.Request, httpResponse: Try[HttpResponse])

}
