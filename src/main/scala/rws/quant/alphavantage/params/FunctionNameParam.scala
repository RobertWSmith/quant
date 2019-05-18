package rws.quant.alphavantage.params

trait FunctionNameParam extends QueryParams {
  println("initializing FunctionNameParam")

  protected val functionName: String

  override def queryMap: Map[String, String] = {
    super.queryMap + ("function" -> this.functionName.trim.toUpperCase())
  }

}
