package rws.quant.alphavantage.timeseries

//import rws.quant.alphavantage.params._
//import rws.quant.alphavantage.timeseries.Daily
//
//val daily: Daily = new Daily("SPY", OutputSizes.Full, "123")
//val ts = daily.daily()

import akka.actor.ActorSystem
import akka.http.scaladsl.common.{
  EntityStreamingSupport,
  JsonEntityStreamingSupport
}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Framing
import akka.util.ByteString
import com.typesafe.config.{Config, ConfigFactory}
import rws.quant.alphavantage.params._
import rws.quant.alphavantage.timeseries.daily.{
  RawTimeSeries,
  RawTimeSeriesJsonSupport
}
import spray.json._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

sealed trait DailyParams extends DataTypeParam with FunctionNameParam {
  final override val functionName: String = "TIME_SERIES_DAILY_ADJUSTED"
  final override val dataType: DataType = DataTypes.JSON
}

class Daily(override val symbolName: String,
            override val outputSize: OutputSize,
            override val apiKey: String)
    extends DailyParams
    with APIKeyParam
    with OutputSizeParam
    with SymbolParam
    with TimeSeriesType
    with SprayJsonSupport
    with RawTimeSeriesJsonSupport {

  def daily(duration: Duration = Duration.Inf): Seq[TimeSeries] = {
    import Daily._

    val httpResponse: HttpResponse =
      Await.result(this.futureResponse(), duration)

    httpResponse match {
      case HttpResponse(StatusCodes.OK, _, entity, _) => {
        val futureDataBytes = entity.dataBytes
          .via(
            Framing.delimiter(
              ByteString("\n"),
              maximumFrameLength = 8096,
              allowTruncation = true
            )
          )
          .runFold(ByteString.empty)(_ ++ _)
        val json =
          Await.result(futureDataBytes, Duration.Inf).utf8String.parseJson
        TimeSeries(json.convertTo[RawTimeSeries])
      }
      case HttpResponse(status, _, _, _) =>
        throw new Exception(status.toString())
      case _ => throw new Exception("No response")
    }
  }

}

object Daily {
  val config: Config = ConfigFactory.load()
  implicit val system: ActorSystem = ActorSystem("ts-daily-system", config)
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  implicit val jsonStreamingSupport: JsonEntityStreamingSupport =
    EntityStreamingSupport.json()
}
