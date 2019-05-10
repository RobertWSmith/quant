package rws.quant.common.utilities

import scala.collection.immutable._
import scala.reflect.{ClassTag, classTag}
import scala.reflect.runtime.universe._

object CaseClassMapper {

  def fromMap[T <: Product: TypeTag: ClassTag](map: Map[String, Any]): T = {
    // https://stackoverflow.com/a.24100624
    val classLoader: ClassLoader = classTag[T].runtimeClass.getClassLoader
    val mirror: Mirror = runtimeMirror(classLoader)
    val classTest: ClassSymbol = typeOf[T].typeSymbol.asClass
    val classMirror: ClassMirror = mirror.reflectClass(classTest)
    val constructor: MethodSymbol =
      typeOf[T].decl(termNames.CONSTRUCTOR).asMethod
    val constructorMirror: MethodMirror =
      classMirror.reflectConstructor(constructor)

    val constructorArgs: List[Any] = constructor.paramLists.flatten.map {
      param: Symbol =>
        {
          param.getClass.cast(
            if (param.typeSignature <:< typeOf[Option[_]])
              map.get(param.name.toString)
            else
              // throws NoSuchElementError if field not found
              map(param.name.toString)
          )
        }
    }
    constructorMirror(constructorArgs: _*).asInstanceOf[T]
  }

  def toMap[T <: Product: TypeTag: ClassTag](klass: T): Map[String, Any] = {
    val values = klass.productIterator
    klass.getClass.getDeclaredFields
      .map { key =>
        {
          val value = values.next() match {
            case p: Product => toMap(p)
            case x          => x
          }
          key.getName -> value
        }
      }
      .toMap[String, Any]
  }

}
