package rws.quant.fred.params

sealed trait Params

trait QueryParams extends Params {

  def queryMap: Map[String, String] = Map.empty

}