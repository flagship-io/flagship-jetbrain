package com.github.flagshipio.jetbrain.cli

import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.dataClass.Feature
import com.google.gson.Gson
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class CheckCLI(private var project: Project) {
    val cli = Cli()

    fun runCli(project: Project) {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "version"
            )
            processBuilder.redirectErrorStream(true) // Redirect error stream to the output stream
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                // Command succeeded
                println("Command completed successfully.")
                println("Output: $output")

            } else {
                // Command failed
                println("Command failed with exit code $exitCode.")
            }

            // Add a shutdown hook to destroy the process when the JVM exits
            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }
    }

    fun listFlagCli(project: Project): List<Feature>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "flag",
                "list",
                "--output-format=json"
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()
            val gson = Gson()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")
                return gson.fromJson(output, Array<Feature>::class.java).toList()

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun listConfigurationCli(project: Project): List<Configuration>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "list",
                "--output-format=json"
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()
            val gson = Gson()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

                return gson.fromJson(output, Array<Configuration>::class.java).toList()

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun checkCli(): Boolean {
        val pluginPath = File("${cli.cliPath}${cli.cliVersion}")
        if (!pluginPath.exists()) {
            // If this version of CodeRefs is not downloaded. Wipe out bin dir of all versions and redownload.
            val binDir = File(cli.cliPath)
            try {
                binDir.deleteRecursively()
                cli.downloadCli()
            } catch (err: Exception) {
                println(err)
                return false
            }
        }
        return true
    }
}