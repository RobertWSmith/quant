package rws.quant.alphavantage.params

trait OutputSizeParam extends QueryParams {
  println("initializing OutputSizeParam")

  val outputSize: OutputSize

  override def queryMap: Map[String, String] = {
    super.queryMap + ("outputsize" -> this.outputSize.toString())
  }

}

sealed trait OutputSize {

  val size: String

  final override def toString(): String = this.size

}

object OutputSizes {

  case object Full extends OutputSize {
    override val size: String = "full"
  }

  case object Compact extends OutputSize {
    override val size: String = "compact"
  }

}
