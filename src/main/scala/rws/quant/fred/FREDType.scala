package rws.quant.fred

import akka.http.scaladsl.model.Uri

trait FREDType {

  final val rootUri: Uri = Uri("https://api.stlouisfed.org")

  val pathUri: Uri.Path = Uri.Path("/fred")

}
