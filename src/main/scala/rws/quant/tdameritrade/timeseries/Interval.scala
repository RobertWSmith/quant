package rws.quant.tdameritrade.timeseries



sealed trait Interval {
  val duration: String

  def toMap(): Map[String, String]
}
