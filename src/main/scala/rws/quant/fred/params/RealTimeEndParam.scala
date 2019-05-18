package rws.quant.fred.params

import java.time.LocalDate
import java.time.format.DateTimeFormatter

trait RealTimeEndParam extends QueryParams {
  println("initializing RealTimeEndParam")

  val realTimeEndDate: LocalDate = LocalDate.now()

  override def queryMap: Map[String, String] = {
    super.queryMap + ("realtime_start" -> this.realTimeEndDate
      .format(DateTimeFormatter.ISO_LOCAL_DATE))
  }

}
