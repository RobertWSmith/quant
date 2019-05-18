package rws.quant.alphavantage.timeseries.daily

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

//"1. open": "285.8400",
//"2. high": "289.2100",
//"3. low": "285.7600",
//"4. close": "288.4300",
//"5. adjusted close": "288.4300",
//"6. volume": "36124736",
//"7. dividend amount": "0.0000",
//"8. split coefficient": "1.0000"

case class Candle(`1. open`: String,
                  `2. high`: String,
                  `3. low`: String,
                  `4. close`: String,
                  `5. adjusted close`: String,
                  `6. volume`: String,
                  `7. dividend amount`: String,
                  `8. split coefficient`: String)

trait CandleJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val candleFormat: RootJsonFormat[Candle] = jsonFormat8(Candle.apply)

  implicit def candleMarshaller: ToEntityMarshaller[Candle] =
    sprayJsonMarshaller[Candle]

  implicit def candleUnmarshaller: FromEntityUnmarshaller[Candle] =
    sprayJsonUnmarshaller[Candle]

}
