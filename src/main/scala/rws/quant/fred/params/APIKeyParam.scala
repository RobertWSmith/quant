package rws.quant.fred.params

trait APIKeyParam extends QueryParams {
  println("initializing APIKeyParam")

  val apiKey: String

  override def queryMap: Map[String, String] = {
    super.queryMap + ("api_key" -> this.apiKey.trim)
  }

}
