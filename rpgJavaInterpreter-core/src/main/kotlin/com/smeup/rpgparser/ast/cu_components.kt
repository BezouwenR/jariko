package com.smeup.rpgparser.ast

import com.smeup.rpgparser.interpreter.AbstractDataDefinition
import com.smeup.rpgparser.interpreter.DataDefinition
import com.smeup.rpgparser.interpreter.InStatementDataDefinition
import com.strumenta.kolasu.model.*
import java.util.*

// This file contains the AST nodes at the highest level:
// from the CompilationUnit (which represents the whole file)
// to its main components

data class CompilationUnit(
    val dataDefinitions: List<DataDefinition>,
    val main: MainBody,
    val subroutines: List<Subroutine>,
    val compileTimeArrays: List<CompileTimeArray>,
    override val position: Position?
) : Node(position) {

    val entryPlist: PlistStmt?
        get() = main.stmts.plist()
                ?: subroutines.mapNotNull { it.stmts.plist() }.firstOrNull()

    private val inStatementsDataDefinitions = LinkedList<InStatementDataDefinition>()

    fun addInStatementDataDefinitions(dataDefinitions: List<InStatementDataDefinition>) {
        inStatementsDataDefinitions.addAll(dataDefinitions)
    }

    @Derived
    val allDataDefinitions: List<AbstractDataDefinition>
        get() {
            val res = LinkedList<AbstractDataDefinition>()
            res.addAll(dataDefinitions)
            dataDefinitions.forEach { it.fields.let { res.addAll(it) } }
            res.addAll(inStatementsDataDefinitions)
            return res
        }

    fun hasDataDefinition(name: String) = dataDefinitions.any { it.name.equals(name, ignoreCase = true) }

    fun getDataDefinition(name: String) = dataDefinitions.first { it.name.equals(name, ignoreCase = true) }

    fun hasAnyDataDefinition(name: String) = allDataDefinitions.any { it.name.equals(name, ignoreCase = true) }

    fun getAnyDataDefinition(name: String) = allDataDefinitions.first { it.name.equals(name, ignoreCase = true) }

    fun compileTimeArray(name: String): CompileTimeArray {
        // TODO: add support for named compile time array
        return compileTimeArrays[0]
    }
}

data class MainBody(val stmts: List<Statement>, override val position: Position? = null) : Node(position)

class Subroutine(override val name: String, val stmts: List<Statement>, override val position: Position? = null) : Named, Node(position)
class Function(override val name: String, override val position: Position? = null) : Named, Node(position)

// TODO describe what a compile time array is
class CompileTimeArray(override val name: String, val lines: List<String>, override val position: Position? = null) : Named, Node(position)

enum class DataWrapUpChoice {
    LR,
    RT
}

// A PList is a list of parameters
fun List<Statement>.plist(): PlistStmt? = this.asSequence().mapNotNull { it as? PlistStmt }.firstOrNull { it.isEntry }
