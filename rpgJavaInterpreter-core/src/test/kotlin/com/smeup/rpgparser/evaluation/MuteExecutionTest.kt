@file:Suppress("DEPRECATION")
package com.smeup.rpgparser.evaluation

import com.smeup.rpgparser.*
import com.smeup.rpgparser.interpreter.*
import com.smeup.rpgparser.jvminterop.JvmProgramRaw
import com.smeup.rpgparser.parsing.parsetreetoast.resolve
import org.junit.Test
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class MuteExecutionTest {

    @Test
    fun executeSimpleMute() {
        val cu = assertASTCanBeProduced("mute/SIMPLE_MUTE", true, withMuteSupport = true)
        cu.resolve()
        assertEquals(3, cu.main.stmts[0].muteAnnotations.size)
        val interpreter = execute(cu, emptyMap())
        assertEquals(3, interpreter.systemInterface.getExecutedAnnotation().size)
        interpreter.systemInterface.getExecutedAnnotation().forEach {
            assertTrue(it.value.succeeded())
        }
    }

    @Test
    fun parsingSimpleMuteTimeout() {
        val cu = assertASTCanBeProduced("mute/SIMPLE_MUTE_TIMEOUT", true, withMuteSupport = true)
        cu.resolve()
        assertEquals(3, cu.main.stmts[0].muteAnnotations.size)
        assertEquals(2, cu.timeouts.size)
        assertEquals(123, cu.timeouts[0].timeout)
        assertEquals(456, cu.timeouts[1].timeout)
    }

    @Test
    fun executionWithShortTimeoutFails() {
        val cu = assertASTCanBeProduced("mute/SIMPLE_MUTE_TIMEOUT_SHORT", true, withMuteSupport = true)
        cu.resolve()
        assertEquals(2, cu.timeouts.size)
        assertEquals(1, cu.timeouts[0].timeout)
        assertEquals(234, cu.timeouts[1].timeout)
        val si = ExtendedCollectorSystemInterface()
        si.programs["Sleep"] =
            object : JvmProgramRaw("Sleep", NumberType(9, 0) parm "millis") {
                override fun execute(systemInterface: SystemInterface, params: LinkedHashMap<String, Value>): List<Value> {
                    val millis = params["millis"]!!.asDecimal().value.toLong()
                    Thread.sleep(millis)
                    return emptyList()
                }
            }
        try {
            execute(cu, emptyMap(), si)
            fail("No timeout")
        } catch (e: TimeoutException) {
            assertTrue(e.toString().contains(cu.timeouts[0].timeout.toString()))
        }
    }

    @Test
    fun executionWithLongTimeoutDoesNotFail() {
        val cu = assertASTCanBeProduced("mute/SIMPLE_MUTE_TIMEOUT_LONG", true, withMuteSupport = true)
        cu.resolve()
        assertEquals(1, cu.timeouts.size)
        assertEquals(12345, cu.timeouts[0].timeout)
        execute(cu, emptyMap())
    }

    @Test
    fun parsingSIMPLE_MUTE_FAIL_STATIC_MESSAGE() {
        val cu = assertASTCanBeProduced("mute/SIMPLE_MUTE_FAIL_STATIC_MESSAGE", true, withMuteSupport = true)
        cu.resolve()
        assertEquals(1, cu.main.stmts[0].muteAnnotations.size)
        val interpreter = execute(cu, emptyMap())
        assertEquals(1, interpreter.systemInterface.getExecutedAnnotation().size)
        val muteAnnotationExecuted = interpreter.systemInterface.getExecutedAnnotation().values.first()
        assertFalse(muteAnnotationExecuted.succeeded())
        assertEquals("This code should not be executed", muteAnnotationExecuted.value1Result.render())
    }

    @Test
    fun parsingSIMPLE_MUTE_FAIL_EVALUATED_MESSAGE() {
        val cu = assertASTCanBeProduced("mute/SIMPLE_MUTE_FAIL_EVALUATED_MESSAGE", true, withMuteSupport = true)
        cu.resolve()
        assertEquals(1, cu.main.stmts[0].muteAnnotations.size)
        val interpreter = execute(cu, emptyMap())
        assertEquals(1, interpreter.systemInterface.getExecutedAnnotation().size)
        val muteAnnotationExecuted = interpreter.systemInterface.getExecutedAnnotation().values.first()
        assertFalse(muteAnnotationExecuted.succeeded())
        assertEquals("Failure message", muteAnnotationExecuted.value1Result.render())
    }
}
