package rws.quant.alphavantage.timeseries

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalTime, ZoneId, ZonedDateTime}

import rws.quant.alphavantage.timeseries.daily.{Candle, RawTimeSeries}

case class TimeSeries(ticker: String,
                      dateTime: ZonedDateTime,
                      open: BigDecimal,
                      high: BigDecimal,
                      low: BigDecimal,
                      close: BigDecimal,
                      adjustedClose: BigDecimal,
                      volume: Long,
                      dividendAmount: BigDecimal,
                      splitCoefficient: BigDecimal,
                      snapshotDateTime: ZonedDateTime)

object TimeSeries {

  def apply(rawTimeSeries: RawTimeSeries): Seq[TimeSeries] = {
    val ticker: String = rawTimeSeries.metadata.`2. Symbol`
    val timeZone: ZoneId = ZoneId.of(rawTimeSeries.metadata.`5. Time Zone`)
    // when was the record extracted?
    val snapshotDateTime: ZonedDateTime =
      ZonedDateTime.now(ZoneId.systemDefault())

    rawTimeSeries.candles.map { p =>
      val candle: Candle = p._2

      val localDate: LocalDate =
        LocalDate.parse(p._1, DateTimeFormatter.ISO_LOCAL_DATE)

      // assign the timestamp to 16:01 for end of day
      val dateTime: ZonedDateTime =
        ZonedDateTime.of(localDate, LocalTime.of(16, 1), timeZone)

      TimeSeries(
        ticker = ticker,
        dateTime = dateTime,
        open = BigDecimal(candle.`1. open`),
        high = BigDecimal(candle.`2. high`),
        low = BigDecimal(candle.`3. low`),
        close = BigDecimal(candle.`4. close`),
        adjustedClose = BigDecimal(candle.`5. adjusted close`),
        volume = candle.`6. volume`.toLong,
        dividendAmount = BigDecimal(candle.`7. dividend amount`),
        splitCoefficient = BigDecimal(candle.`8. split coefficient`),
        snapshotDateTime = snapshotDateTime
      )
    }.toSeq
  }

}
