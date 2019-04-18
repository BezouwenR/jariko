package com.smeup.rpgparser

import com.smeup.rpgparser.ast.DataRefExpr
import com.smeup.rpgparser.ast.Expression

// Supported data types:
// * Character Format
// * Numeric Data Type
// * UCS-2 Format
// * Date Data Type
// * Time Data Type
// * Timestamp Data Type
// * Object Data Type
// * Basing Pointer Data Type
// * Procedure Pointer Data Type

sealed class Type
class DataStructureType(val fields: List<FieldType>, val elementSize: Int) : Type()
// TODO remove this one
@Deprecated(message = "Replace with DataStructureType")
class DataDefinitionType(val dataDefinition: AbstractDataDefinition) : Type()
class StringType(val length: Long) : Type()
object BooleanType : Type()
class NumberType(val entireDigits: Int, val decimalDigits: Int) : Type()
data class ArrayType(val element: Type, val nElements: Int) : Type()

data class FieldType(val name: String, val type: Type)

fun Expression.type() : Type {
    return when (this) {
        is DataRefExpr -> this.variable.referred!!.type()
        else -> TODO(this.javaClass.canonicalName)
    }
}

fun AbstractDataDefinition.type(): Type {
    when (this) {
        is FieldDefinition -> {
            // TODO consider data type
            TODO()
//            val baseType = RawType(this.size)
//            return if (container.isArray()) {
//                ArrayType(baseType)
//            } else {
//                baseType
//            }
        }
        is DataDefinition -> {
            TODO()
            //return RawType(this.size)
        }
        is InStatementDataDefinition -> {
            TODO()
            // TODO consider data type
            //return RawType(this.size)
        }
        else -> TODO(this.javaClass.canonicalName)
    }
}