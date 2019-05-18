package rws.quant.fred.params

trait DataTypeParam extends QueryParams {
  println("initializing DataTypeParam")

  val dataType: DataType

  override def queryMap: Map[String, String] = {
    super.queryMap + ("file_type" -> this.dataType.toString())
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

  case object XML extends DataType {
    override val dataType: String = "xml"
  }

}
