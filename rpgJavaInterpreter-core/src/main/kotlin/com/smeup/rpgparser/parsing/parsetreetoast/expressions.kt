package com.smeup.rpgparser.parsing.parsetreetoast

import com.smeup.rpgparser.RpgParser
import com.smeup.rpgparser.parsing.ast.*
import com.strumenta.kolasu.mapping.toPosition
import com.strumenta.kolasu.model.ReferenceByName
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale



internal fun RpgParser.SimpleExpressionContext.toAst(conf: ToAstConfiguration = ToAstConfiguration()): Expression {
    return when {
        this.number() != null -> this.number()!!.toAst(conf)
        this.identifier() != null -> this.identifier().toAst(conf)
        this.bif() != null -> this.bif().toAst(conf)
        this.literal() != null -> this.literal().toAst(conf)
        else -> TODO(this.javaClass.canonicalName)
    }
}

fun RpgParser.ExpressionContext.toAst(conf: ToAstConfiguration = ToAstConfiguration()): Expression {
    return when {
        this.number() != null -> this.number()!!.toAst(conf)
        this.identifier() != null -> this.identifier().toAst(conf)
        this.bif() != null -> this.bif().toAst(conf)
        this.literal() != null -> this.literal().toAst(conf)
        this.EQUAL() != null -> EqualityExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        this.OR() != null -> LogicalOrExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        this.AND() != null -> LogicalAndExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        this.comparisonOperator() != null -> when {
            this.comparisonOperator().GT() != null -> GreaterThanExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
            this.comparisonOperator().GE() != null -> GreaterEqualThanExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
            this.comparisonOperator().LT() != null -> LessThanExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
            this.comparisonOperator().LE() != null -> LessEqualThanExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
            this.comparisonOperator().NE() != null -> DifferentThanExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
            else -> TODO("ComparisonOperator ${this.comparisonOperator().text}")
        }
        this.function() != null -> this.function().toAst(conf)
        this.NOT() != null -> NotExpr(this.expression(0).toAst(conf), toPosition(conf.considerPosition))
        this.PLUS() != null -> PlusExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        this.MINUS() != null -> MinusExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        this.MULT() != null || this.MULT_NOSPACE() != null -> MultExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        this.DIV() != null -> DivExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        this.EXP() != null -> ExpExpr(this.expression(0).toAst(conf), this.expression(1).toAst(conf))
        // FIXME it is rather ugly that we have to do this: we should get a different parse tree here
        this.children.size == 3 && this.children[0].text == "(" && this.children[2].text == ")"
                && this.children[1] is RpgParser.ExpressionContext -> (this.children[1] as RpgParser.ExpressionContext).toAst(conf)
        else -> TODO(this.text.toString())
    }
}

internal fun RpgParser.LiteralContext.toAst(conf: ToAstConfiguration = ToAstConfiguration()): Expression {
    return StringLiteral(this.content?.text ?: "", toPosition(conf.considerPosition))
}

internal fun RpgParser.NumberContext.toAst(conf: ToAstConfiguration = ToAstConfiguration()): NumberLiteral {
    val position = this.toPosition(conf.considerPosition)
    require(this.NumberPart().isEmpty(), { "Number not empty $position" })
    val text = (this.MINUS()?.text ?: "") + this.NUMBER().text

    // TODO Maurizio
    // Requirement:
    // Quando si assegna direttamente un valore ad un campo numerico,
    // si può utilizzare indifferentemente come separatore decimale il punto o la virgola
    return if (text.contains('.')) {
        val nf = NumberFormat.getNumberInstance(Locale.US)
        val bd = BigDecimal(nf.parse(text).toString())
        RealLiteral(bd,position)
        //RealLiteral(text.toBigDecimal(), position)
    } else if(text.contains(',')){
        val nf = NumberFormat.getNumberInstance(Locale.ITALIAN)
        val bd = BigDecimal(nf.parse(text).toString())
        RealLiteral(bd,position)
    } else {
        IntLiteral(text.toLong(), position)
    }
}

internal fun RpgParser.IdentifierContext.toAst(conf: ToAstConfiguration = ToAstConfiguration()): Expression {
    return when (this.text.toUpperCase()) {
        "*BLANK", "*BLANKS" -> BlanksRefExpr(toPosition(conf.considerPosition))
        "*ZERO", "*ZEROS" -> TODO()
        "*HIVAL" -> HiValExpr(toPosition(conf.considerPosition))
        "*LOVAL" -> LowValExpr(toPosition(conf.considerPosition))
        "*ON" -> OnRefExpr(toPosition(conf.considerPosition))
        "*OFF" -> OffRefExpr(toPosition(conf.considerPosition))
        else -> when {
            this.text.indicatorIndex() != null -> PredefinedIndicatorExpr(
                    this.text.indicatorIndex()!!,
                    toPosition(conf.considerPosition))
            else -> DataRefExpr(variable = ReferenceByName(this.text), position = toPosition(conf.considerPosition))
        }
    }
}

internal fun String.indicatorIndex(): Int? {
    return if (this.startsWith("*IN")) {
        this.substring("*IN".length).toIntOrNull()
    } else {
        null
    }
}
