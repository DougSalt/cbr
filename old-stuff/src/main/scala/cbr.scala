import org.nlogo.core.Syntax
import org.nlogo.core.Syntax.{ListType, NumberType, BooleanType, StringType, RepeatableType}
import org.nlogo.core.Program
import org.nlogo.core.Breed
import org.nlogo.api._

import java.io.File
//import java.io.FileWriter
//import java.util.ArrayList
//import java.util.Arrays
//import java.util.Collection
//import java.util.HashMap
//import java.util.HashSet
//import java.util.Map
//import java.util.Set

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.language.postfixOps

class CaseBasedReasoningExtension extends DefaultClassManager {

  override def load(manager: PrimitiveManager) {
    manager.addPrimitive("table", new Table)
    manager.addPrimitive("getValue", new GetValue)
    manager.addPrimitive("getKeyedValue", new GetKeyedValue)
  }
}

object CaseBasedReasoning {
  var treePath: String = _
  var dataPath: String = _
  var table: Map[String, CaseBasedReasoning] = _
  def apply(treePath: String, dataPath: String): CaseBasedReasoning = {
      var lt = new CaseBasedReasoning
      lt.treeFile = new File(treePath)
      lt.dataFile = new File(dataPath)
      lt
  }
}

class CaseBasedReasoning {

  var treeFile: File  = _
  var dataFile: File  = _
  var key = randomKey
  CaseBasedReasoning.table(key) = this

  def randomKey: String = {
    "aRandomKey"
  }
  def getKey: String = {
    key
  }
  def getValues(keys: Array[Argument]): String = {
    "aParticularValue"
  }
  def getKeyedValues(keys: Array[Argument]): String = {
    "aParticularValue"
  }
}

class Table extends Reporter {
  override def getSyntax = Syntax.reporterSyntax(right = List(StringType, StringType), ret=StringType)
  override def report(args: Array[Argument], context: Context): AnyRef = {
    val tree = try args(0).getString 
    catch {
      case e: LogoException =>
        throw new ExtensionException(e.getMessage)
    }
    val data = try args(1).getString 
    catch {
      case e: LogoException =>
        throw new ExtensionException(e.getMessage)
    }
    CaseBasedReasoning(tree,data).getKey
  }
}

class GetValue extends Reporter {
  override def getSyntax = Syntax.reporterSyntax(right = List(StringType, NumberType | RepeatableType), ret=StringType)
  override def report(args: Array[Argument], context: Context): AnyRef = {
    val lt = try args(0).getString 
    catch {
      case e: LogoException =>
        throw new ExtensionException(e.getMessage.drop(1))
    }
    CaseBasedReasoning.table(lt).getValues(args)
  }
}

class GetKeyedValue extends Reporter {
  override def getSyntax =  Syntax.reporterSyntax(right = List(StringType, StringType | RepeatableType), ret=StringType)
  override def report(args: Array[Argument], context: Context): AnyRef = {
    val lt = try args(0).getString 
    catch {
      case e: LogoException =>
        throw new ExtensionException(e.getMessage)
    }
    CaseBasedReasoning.table(lt).getKeyedValues(args.drop(1))
  }
}


