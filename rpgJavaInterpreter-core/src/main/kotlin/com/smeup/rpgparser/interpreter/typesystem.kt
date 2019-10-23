package com.smeup.rpgparser.interpreter

import com.smeup.rpgparser.parsing.ast.*
import java.math.BigDecimal

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

sealed class Type {
    open fun numberOfElements(): Int {
        return 1
    }
    open fun elementSize(): Long {
        return size
    }

    open fun canBeAssigned(value: Value): Boolean {
        return value.assignableTo(this)
    }

    abstract val size: Long
}
object KListType : Type() {
    override val size: Long
        get() = 0

    override fun canBeAssigned(value: Value): Boolean = false
}
data class DataStructureType(val fields: List<FieldType>, val elementSize: Int) : Type() {

    override val size: Long
        get() = elementSize.toLong()
}

data class StringType(val length: Long) : Type() {
    override val size: Long
        get() = length
}

object BooleanType : Type() {
    override val size: Long
        get() = 1
}

object TimeStampType : Type() {
    override val size: Long
        get() = 26
}

data class CharacterType(val nChars: Int) : Type() {
    override val size: Long
        get() = nChars.toLong()
}

data class NumberType(val entireDigits: Int, val decimalDigits: Int, val rpgType: String? = "") : Type() {
    override val size: Long
        get() = (entireDigits + decimalDigits).toLong()
    val integer: Boolean
        get() = decimalDigits == 0
    val decimal: Boolean
        get() = !integer
}

data class ArrayType(val element: Type, val nElements: Int, val compileTimeRecordsPerLine: Int? = null) : Type() {
    override val size: Long
        get() = element.size * nElements

    override fun numberOfElements(): Int {
        return nElements
    }

    override fun elementSize(): Long {
        return element.size
    }

    fun compileTimeArray(): Boolean = compileTimeRecordsPerLine != null
}

data class FieldType(val name: String, val type: Type)

fun Expression.type(): Type {
    return when (this) {
        is DataRefExpr -> {
            this.variable.referred!!.type
        }
        is StringLiteral -> {
            StringType(this.value.length.toLong())
        }
        is IntLiteral -> {
            NumberType(BigDecimal.valueOf(this.value).precision(), decimalDigits = 0)
        }
        is RealLiteral -> {
            NumberType(this.value.precision() - this.value.scale(), this.value.scale())
        }
        else -> TODO("We do not know how to calculate the type of $this (${this.javaClass.canonicalName})")
    }
}
