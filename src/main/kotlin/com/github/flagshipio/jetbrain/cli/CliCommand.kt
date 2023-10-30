package com.github.flagshipio.jetbrain.cli

import com.github.flagshipio.jetbrain.dataClass.*
import com.google.gson.Gson
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.extensions.PluginId
import java.io.File

@Service(Service.Level.PROJECT)
class CliCommand {
    private val cli = Cli()
    private val gson = Gson()

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
            if (configurationName != newConfiguration.name) {
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

    fun currentConfigurationCli(): Configuration? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "configuration",
                "current",
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
                return gson.fromJson(output, Configuration::class.java)

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

    fun addFlagCli(flag: Flag): Flag? {
        println("running")
        try {

            val flagDataRaw = if (flag.type == "boolean") {
                "-d={\"name\":\"${flag.name}\",\"type\":\"${flag.type}\",\"source\":\"cli\",\"description\":\"${flag.description}\"}"
            } else {
                "-d={\"name\":\"${flag.name}\",\"type\":\"${flag.type}\",\"source\":\"cli\",\"description\":\"${flag.description}\",\"default_value\":\"${flag.defaultValue}\"}"
            }

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "flag",
                "create",
                flagDataRaw,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, Flag::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun editFlagCli(flagID: String, newFlag: Flag): Flag? {
        println("running")
        try {

            val flagDataRaw = if (newFlag.type == "boolean") {
                "-d={\"name\":\"${newFlag.name}\",\"type\":\"${newFlag.type}\",\"source\":\"cli\",\"description\":\"${newFlag.description}\"}"
            } else {
                "-d={\"name\":\"${newFlag.name}\",\"type\":\"${newFlag.type}\",\"source\":\"cli\",\"description\":\"${newFlag.description}\",\"default_value\":\"${newFlag.defaultValue}\"}"
            }

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "flag",
                "edit",
                "-i=$flagID",
                flagDataRaw,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, Flag::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun deleteFlagCli(flagID: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "flag",
                "delete",
                "-i=$flagID",
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

    fun addGoalCli(goal: Goal): Goal? {
        println("running")
        try {

            val goalDataRaw = if ((goal.type == "transaction") || (goal.type == "event")) {
                "-d={\"label\":\"${goal.label}\",\"type\":\"${goal.type}\"}"
            } else {
                "-d={\"label\":\"${goal.label}\",\"type\":\"${goal.type}\",\"operator\":\"${goal.operator}\",\"value\":\"${goal.value}\"}"
            }

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "goal",
                "create",
                goalDataRaw,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, Goal::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun editGoalCli(goalID: String, newGoal: Goal): Goal? {
        println("running")
        try {
            val goalDataRaw = if ((newGoal.type == "transaction") || (newGoal.type == "event")) {
                "-d={\"label\":\"${newGoal.label}\"}"
            } else {
                "-d={\"label\":\"${newGoal.label}\",\"operator\":\"${newGoal.operator}\",\"value\":\"${newGoal.value}\"}"
            }

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "goal",
                "edit",
                "-i=$goalID",
                goalDataRaw,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, Goal::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun deleteGoalCli(goalID: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "goal",
                "delete",
                "-i=$goalID",
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

    fun listGoalCli(): List<Goal>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "goal",
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
                return gson.fromJson(output, Array<Goal>::class.java).toList()

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

    fun addTargetingKeyCli(targetingKey: TargetingKey): TargetingKey? {
        println("running")
        try {

            val targetingKeyDataRaw = "-d={\"name\":\"${targetingKey.name}\",\"type\":\"${targetingKey.type}\",\"description\":\"${targetingKey.description}\"}"

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "targeting-key",
                "create",
                targetingKeyDataRaw,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, TargetingKey::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun editTargetingKeyCli(targetingKeyID: String, newTargetingKey: TargetingKey): TargetingKey? {
        println("running")
        try {
            val targetingKeyDataRaw = "-d={\"name\":\"${newTargetingKey.name}\",\"type\":\"${newTargetingKey.type}\",\"description\":\"${newTargetingKey.description}\"}"


            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "targeting-key",
                "edit",
                "-i=$targetingKeyID",
                targetingKeyDataRaw,
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, TargetingKey::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun deleteTargetingKeyCli(targetingKeyID: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "targeting-key",
                "delete",
                "-i=$targetingKeyID",
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

    fun listTargetingKeyCli(): List<TargetingKey>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "targeting-key",
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
                return gson.fromJson(output, Array<TargetingKey>::class.java).toList()

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

    fun addProjectCli(project: Project): Project? {
        println("running")
        try {

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "project",
                "create",
                "-n=${project.name}",
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, Project::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun editProjectCli(projectID: String, newProject: Project): Project? {
        println("running")
        try {

            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "project",
                "edit",
                "-i=$projectID",
                "-n=${newProject.name}",
                "--user-agent=flagship-ext-jetbrain/v" + (PluginManagerCore.getPlugin(PluginId.getId("com.github.flagshipio.jetbrain"))?.version
                    ?: "")
            )
            processBuilder.redirectErrorStream(true)
            processBuilder.command()
            val process = processBuilder.start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Command completed successfully.")

            } else {
                println("Command failed with exit code $exitCode.")
            }

            Runtime.getRuntime().addShutdownHook(Thread { process.destroy() })
            return gson.fromJson(output, Project::class.java)

        } catch (exception: Exception) {
            println(exception)
            println("didn't work")
        }

        return null
    }

    fun deleteProjectCli(projectID: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "project",
                "delete",
                "-i=$projectID",
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

    fun listProjectCli(): List<Project>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "project",
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
                return gson.fromJson(output, Array<Project>::class.java).toList()

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

    fun switchProjectStateCli(projectID: String, status: String): String? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "project",
                "switch",
                "-i=$projectID",
                "-s=$status",
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
                println(output)

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

    fun listCampaignCli(): List<Campaign>? {
        println("running")
        try {
            val processBuilder = ProcessBuilder(
                PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/" + cli.cliVersion + "/flagship",
                "campaign",
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
                println(output)

                return gson.fromJson(output, Array<Campaign>::class.java).toList()

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