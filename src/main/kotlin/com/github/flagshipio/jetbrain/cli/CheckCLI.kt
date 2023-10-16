package com.github.flagshipio.jetbrain.cli

import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.google.gson.Gson
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class CheckCLI() {
    private val cli = Cli()
    private val gson = Gson()


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

    fun listFlagCli(): List<Flag>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "flag",
                "list",
                "--output-format=json",
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")
                return gson.fromJson(output, Array<Flag>::class.java).toList()

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

    fun addConfigurationCli(configuration: Configuration): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "create",
                "-n=" + configuration.name,
                "-i=" + configuration.clientID,
                "-s=" + configuration.clientSecret,
                "-a=" + configuration.accountID,
                "-e=" + configuration.accountEnvironmentID,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            Notifications.Bus.notify(
                Notification(
                    Cli.FLAGSHIP_CLI_ID,
                    "Flagship",
                    output,
                    NotificationType.INFORMATION
                )
            )

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return output
        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun addConfigurationFromFileCli(filePath: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "create",
                "-p=$filePath",
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            Notifications.Bus.notify(
                Notification(
                    Cli.FLAGSHIP_CLI_ID,
                    "Flagship",
                    output,
                    NotificationType.INFORMATION
                )
            )

            if (exitCode == 0) {
                println("Command completed successfully.")
            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return output
        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun listConfigurationCli(): List<Configuration>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "list",
                "--output-format=json",
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

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

    fun editConfigurationCli(configurationName: String, newConfiguration: Configuration): String? {
        println("running")
        try {
            var editCommand = arrayOf("")
            if (configurationName != newConfiguration.name){
                editCommand = arrayOf("--new-name=" + newConfiguration.name)
            }

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "edit",
                "-n=$configurationName",
                "-i=" + newConfiguration.clientID,
                "-s=" + newConfiguration.clientSecret,
                "-a=" + newConfiguration.accountID,
                "-e=" + newConfiguration.accountEnvironmentID,
                *editCommand,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()
            println(output)

            if (exitCode == 0) {
                println("Command completed successfully.")
                return output

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

    fun deleteConfigurationCli(configurationName: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "delete",
                "-n=$configurationName",
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")
                return output

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

    fun useConfigurationCli(configurationName: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "use",
                "-n=$configurationName",
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")
                return output

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