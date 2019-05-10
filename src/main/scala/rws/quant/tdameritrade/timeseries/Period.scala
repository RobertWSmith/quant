package rws.quant.tdameritrade.timeseries

sealed trait PeriodType {
  val periodType: String

  val validPeriods: Seq[Int]

  val defaultPeriods: Int

  final def toMap(): Map[String, String] = Map("periodType" -> this.periodType)

  override final def toString(): String = this.periodType
}

object PeriodTypes {

  case object Day extends PeriodType {
    override val periodType: String = "day"
    override val defaultPeriods: Int = 10
    override val validPeriods: Seq[Int] = Seq(1, 2, 3, 4, 5, 10)
  }

  case object Month extends PeriodType {
    override val periodType: String = "month"
    override val defaultPeriods: Int = 1
    override val validPeriods: Seq[Int] = Seq(1, 2, 3, 6)
  }

  case object Year extends PeriodType {
    override val periodType: String = "year"
    override val defaultPeriods: Int = 1
    override val validPeriods: Seq[Int] = Seq(1, 2, 3, 5, 10, 15, 20)
  }

  case object YearToDate extends PeriodType {
    override val periodType: String = "ytd"
    override val defaultPeriods: Int = 1
    override val validPeriods: Seq[Int] = Seq(1)
  }

  val YTD: PeriodType = YearToDate

}

case class Period(periods: Int, periodType: PeriodType) {
  assert(this.periodType.validPeriods.contains(periods))

  final def toMap(): Map[String, String] =
    Map(
      "periodType" -> this.periodType.toString(),
      "periods" -> this.periods.toString()
    )
}

object Period {

  def apply(periodType: PeriodType): Period =
    Period(periodType.defaultPeriods, periodType)

}
