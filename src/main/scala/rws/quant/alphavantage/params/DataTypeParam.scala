package rws.quant.alphavantage.params

trait DataTypeParam extends QueryParams {
  println("initializing DataTypeParam")

  val dataType: DataType

  override def queryMap: Map[String, String] = {
    super.queryMap + ("datatype" -> this.dataType.toString())
  }

}

sealed trait DataType {

  val dataType: String

  final override def toString(): String = this.dataType

}

object DataTypes {

  case object JSON extends DataType {
    override val dataType: String = "json"
  }

  case object CSV extends DataType {
    override val dataType: String = "csv"
  }

}
