package rws.quant.tdameritrade.timeseries

import java.time.ZonedDateTime

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.{HttpHeader, Uri}

import scala.collection.immutable.Seq

class TimeSeries extends Actor with ActorLogging {

  import Query.{Request => QRequest, Response => QResponse}
  import TimeSeries.{Request => TSRequest}

  val queryActor: ActorRef = context.actorOf(Props[Query], "query")

  override def receive: Receive = {
    case r: TSRequest => queryActor ! QRequest(sender(), r)

    case QResponse(QRequest(origSender, _), httpResponse) =>
      origSender ! httpResponse
  }

}

object TimeSeries {

  sealed trait Request {
    val symbol: String
    val apiKey: String
    val needExtendedHoursData: Boolean
    val frequencyType: String
    val frequency: Int
    val token: Option[String] = None

    def queryMap(): Map[String, String] = Map(
      "symbol" -> this.symbol,
      "apikey" -> this.apiKey,
      "frequencyType" -> this.frequencyType,
      "frequency" -> this.frequency.toString,
      "needExtendedHoursData" -> this.needExtendedHoursData.toString.toLowerCase
    )

    final def getQuery(): Uri.Query = Uri.Query(this.queryMap())

    final def getHeaders(): Seq[HttpHeader] = {
      Seq(
        `Accept-Encoding`(
          HttpEncodings.gzip,
          HttpEncodings.deflate,
          HttpEncodings.chunked
        ),
        `Accept-Language`(LanguageRange("en-US"), LanguageRange("en", 0.5f)),
        Host("api.tdameritrade.com")
      ) ++ (this.token match {
        case Some(t) => Seq(Authorization(OAuth2BearerToken(t)))
        case None    => Seq()
      })
    }
  }

  case class ExactTimePeriods(symbol: String,
                              apiKey: String,
                              startDate: ZonedDateTime,
                              endDate: ZonedDateTime,
                              frequencyType: String,
                              frequency: Int = 1,
                              needExtendedHoursData: Boolean = false)
      extends Request {
    override def queryMap(): Map[String, String] = {
      super.queryMap() ++
        Map(
          "startDate" -> this.startDate.toInstant.toEpochMilli.toString,
          "endDate" -> this.endDate.toInstant.toEpochMilli.toString
        )
    }
  }

  case class RelativeTimePeriods(symbol: String,
                                 apiKey: String,
                                 periodType: String,
                                 period: Int,
                                 frequencyType: String,
                                 frequency: Int = 1,
                                 needExtendedHoursData: Boolean = false)
      extends Request {
    override def queryMap(): Map[String, String] = {
      super.queryMap() ++
        Map("periodType" -> this.periodType, "period" -> this.period.toString)
    }
  }

}
