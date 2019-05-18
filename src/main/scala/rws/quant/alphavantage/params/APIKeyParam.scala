package rws.quant.alphavantage.params

trait APIKeyParam extends QueryParams {
  println("initializing APIKeyParam")

  val apiKey: String

  override def queryMap: Map[String, String] = {
    super.queryMap + ("apikey" -> this.apiKey.trim)
  }

}
