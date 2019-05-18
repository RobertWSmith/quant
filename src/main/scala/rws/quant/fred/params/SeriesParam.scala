package rws.quant.fred.params

trait SeriesParam extends QueryParams {
  println("initializing SeriesParam")

  val seriesId: String

  override def queryMap: Map[String, String] = {
    super.queryMap + ("series_id" -> this.seriesId)
  }

}
