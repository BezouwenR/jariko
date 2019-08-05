package com.smeup.rpgparser.execution

import com.smeup.rpgparser.utils.measureAndCatch
import java.io.*

// TODO describe what this program does
class SimpleShell {

    private val exitCommands = hashSetOf("exit", "quit", "signoff", "off")

    fun repl(r: (parms: Array<String>) -> Unit) {
        var commandLine: String
        val console = BufferedReader(InputStreamReader(System.`in`))

        while (true) {
            print("rpg>")
            commandLine = console.readLine().trim()
            run {
                if (exitCommands.contains(commandLine.toLowerCase())) {
                    println("Goodbye")
                    System.exit(0)
                }
                val args = commandLine.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (!args.isEmpty()) {
                    val timeElapsed = measureAndCatch {
                        r(args)
                    }
                    println("Function executed in $timeElapsed milliseconds")
                }
            }
        }
    }
}
