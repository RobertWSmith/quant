package rws.quant.tdameritrade

import java.time.{ZoneId, ZonedDateTime}
import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import rws.quant.tdameritrade.timeseries.TimeSeries

import scala.concurrent.ExecutionContext

object TDAmeritrade {

  implicit val actorSystem: ActorSystem = ActorSystem("TDAmeritrade")
  implicit val timeout: Timeout = Timeout(60L, TimeUnit.SECONDS)
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  val timeSeriesActor: ActorRef =
    actorSystem.actorOf(Props[TimeSeries], "timeSeries")

  def main(args: Array[String]): Unit = {
    println(this.actorSystem)
    println(this.timeSeriesActor.path)

    val exactPeriod: TimeSeries.Request = TimeSeries.ExactTimePeriods(
      "SPY",
      "AU3C245KSSRTM",
      ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("US/Eastern")),
      ZonedDateTime.now(),
      "daily"
    )

    (this.timeSeriesActor ? exactPeriod).onComplete { response =>
      println(response.toString)
    }

  }

}
