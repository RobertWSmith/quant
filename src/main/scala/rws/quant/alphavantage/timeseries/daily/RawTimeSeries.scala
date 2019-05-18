package rws.quant.alphavantage.timeseries.daily

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import spray.json.{RootJsonFormat, _}

case class RawTimeSeries(metadata: Metadata, candles: Map[String, Candle])

trait RawTimeSeriesJsonSupport
    extends SprayJsonSupport
    with DefaultJsonProtocol
    with MetadataJsonSupport
    with CandleJsonSupport {

  implicit object rawTimeSeriesFormat extends RootJsonFormat[RawTimeSeries] {

    def write(ts: RawTimeSeries): JsValue = {
      val metadata: JsValue = metadataFormat.write(ts.metadata)
      val candles: Map[String, JsValue] = ts.candles.map { x =>
        x._1 -> candleFormat.write(x._2)
      }

      JsObject(
        "Meta Data" -> metadata,
        "Time Series (Daily)" -> JsObject(candles)
      )
    }

    def read(json: JsValue): RawTimeSeries = {
      json.asJsObject.getFields("Meta Data", "Time Series (Daily)") match {
        case Seq(JsObject(metadata), JsObject(timeSeries)) => {
          val md: Metadata =
            metadataFormat.read(JsObject(metadata))
          val ts: Map[String, Candle] = timeSeries.map { x =>
            x._1 -> candleFormat.read(x._2)
          }

          RawTimeSeries(md, ts)
        }
        case _ => throw DeserializationException("couldn't parse input JSON")
      }
    }
  }

  implicit def rawTimeSeriesMarshaller: ToEntityMarshaller[RawTimeSeries] =
    sprayJsonMarshaller[RawTimeSeries]

  implicit def rawTimeSeriesUnmarshaller: FromEntityUnmarshaller[RawTimeSeries] =
    sprayJsonUnmarshaller[RawTimeSeries]
}
