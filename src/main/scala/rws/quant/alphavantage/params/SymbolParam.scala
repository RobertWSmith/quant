package rws.quant.alphavantage.params

trait SymbolParam extends QueryParams {
  println("initializing SymbolParam")

  val symbolName: String

  override def queryMap: Map[String, String] = {
    super.queryMap + ("symbol" -> this.symbolName.trim.toUpperCase())
  }

}
