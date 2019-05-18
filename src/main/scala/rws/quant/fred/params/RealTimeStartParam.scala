package rws.quant.fred.params

import java.time.LocalDate
import java.time.format.DateTimeFormatter

trait RealTimeStartParam extends QueryParams {
  println("initializing RealTimeStartParam")

  val realTimeStartDate: LocalDate = LocalDate.now()

  override def queryMap: Map[String, String] = {
    super.queryMap + ("realtime_start" -> this.realTimeStartDate
      .format(DateTimeFormatter.ISO_LOCAL_DATE))
  }

}
