package rws.quant.alphavantage.timeseries

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import rws.quant.alphavantage.params.QueryParams

import scala.concurrent.Future

trait TimeSeriesType {

  this: QueryParams =>

  final val rootUri: Uri = Uri("https://www.alphavantage.co")

  final val pathUri: Uri.Path = Uri.Path("/query")

  final def queryUri: Uri.Query = Uri.Query(this.queryMap)

  final val uri: Uri = {
    this.rootUri.withPath(this.pathUri).withQuery(this.queryUri)
  }

  final def futureResponse()(implicit system: ActorSystem): Future[HttpResponse] = {
    val futureResponse: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = this.uri))
    futureResponse
  }

}
