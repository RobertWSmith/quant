package rws.quant.alphavantage.timeseries.daily

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import spray.json._

case class Metadata(`1. Information`: String,
                    `2. Symbol`: String,
                    `3. Last Refreshed`: String,
                    `4. Output Size`: String,
                    `5. Time Zone`: String)

trait MetadataJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val metadataFormat: RootJsonFormat[Metadata] = jsonFormat5(
    Metadata.apply
  )

  implicit def metadataMarshaller: ToEntityMarshaller[Metadata] =
    sprayJsonMarshaller[Metadata]

  implicit def metadataUnmarshaller: FromEntityUnmarshaller[Metadata] =
    sprayJsonUnmarshaller[Metadata]

}
