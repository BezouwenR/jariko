package com.smeup.rpgparser.evaluation

import com.smeup.rpgparser.*
import kotlin.test.assertEquals
import org.junit.Test
import org.junit.experimental.categories.Category
import kotlin.test.Ignore
import kotlin.test.assertTrue

class MUTEExamplesTest {

    @Test @Category(PerformanceTest::class)
    fun executeMUTE10_01_perf_calls() {
        val si = ExtendedCollectorSystemInterface()
        si.programFinders.add(DummyProgramFinder("/performance/"))
        execute("performance/MUTE10_01", mapOf(), si)
        assertTrue(si.displayed.isEmpty())
    }

    @Test @Category(PerformanceTest::class)
    fun executeMUTE10_04_perf_strings() {
        assertEquals(listOf("Loop 1", "Loop 2", "Loop 3", "Loop 4", "Loop 5", "Loop 6"), outputOf("performance/MUTE10_04"))
    }

    @Test @Category(PerformanceTest::class)
    fun executeMUTE10_04A_perf_strings() {
        assertEquals(listOf("001.1_d01.1_A01.1_c01.1_B01.1_b01.1_C01.1_901.1_101."), outputOf("performance/MUTE10_04A"))
    }

    @Test @Category(PerformanceTest::class) @Ignore
    fun executeMUTE10_02() {
        assertEquals(emptyList(), outputOf("performance/MUTE10_02"))
    }

    @Test @Category(PerformanceTest::class) @Ignore
    fun executeMUTE10_03() {
        assertEquals(emptyList(), outputOf("performance/MUTE10_03"))
    }
}
