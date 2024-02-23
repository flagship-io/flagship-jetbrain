package com.github.flagshipio.jetbrain.cli

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.ThrowableComputable
import com.intellij.util.download.DownloadableFileDescription
import com.intellij.util.download.DownloadableFileService
import com.intellij.util.io.Decompressor
import com.intellij.util.system.CpuArch
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

class Cli {
    val cliVersion = "1.0.1"
    val cliPath = PathManager.getPluginsPath() + "/flagship-jetbrain/bin/cli/"

    fun downloadCli(project: Project? = null): DownloadResult {
        val result = ProgressManager.getInstance()
            .runProcessWithProgressSynchronously(
                ThrowableComputable<DownloadResult, Nothing> {
                    downloadCliSynchronously()
                },
                "Download CLI From Flagship",
                true,
                project
            )

        when (result) {
            is DownloadResult.Ok -> {
                Notifications.Bus.notify(
                    Notification(
                        FLAGSHIP_CLI_ID,
                        "Flagship",
                        "CLI successfully downloaded",
                        NotificationType.INFORMATION
                    )
                )
            }

            DownloadResult.Failed -> {
                Notifications.Bus.notify(
                    Notification(
                        FLAGSHIP_CLI_ID,
                        "Flagship",
                        "CLI",
                        NotificationType.ERROR
                    )
                )
            }
        }

        return result
    }

    private fun downloadCliSynchronously(): DownloadResult {
        val downloadUrl = cliUrl
        return try {
            val crDir = downloadAndUnarchive(downloadUrl.toString())
            DownloadResult.Ok(crDir)
        } catch (e: IOException) {
            println("Can't download CLI: $e")
            DownloadResult.Failed
        }
    }

    @Throws(IOException::class)
    private fun downloadAndUnarchive(crUrl: String): File {
        val service = DownloadableFileService.getInstance()
        val downloadDesc = service.createFileDescription(crUrl)
        val downloader = service.createDownloader(listOf(downloadDesc), "CLI downloading")
        val downloadDirectory = downloadPath().toFile()
        val downloadResults = downloader.download(downloadDirectory)
        val pluginPath = File("$cliPath$cliVersion")
        pluginPath.mkdir()
        for (result in downloadResults) {
            val archiveFile = result.first
            Unarchiver.unarchive(archiveFile, pluginPath)
            archiveFile.delete()
        }
        return pluginPath
    }

    private fun DownloadableFileService.createFileDescription(url: String): DownloadableFileDescription {
        val fileName = url.substringAfterLast("/")
        return createFileDescription(url, fileName)
    }

    companion object {
        const val FLAGSHIP_CLI_ID = "Flagship CLI"

        private fun downloadPath(): Path = Paths.get(PathManager.getTempPath())
    }

    private enum class Unarchiver {
        ZIP {
            override val extension: String = "zip"
            override fun createDecompressor(file: File): Decompressor = Decompressor.Zip(file)
        },
        TAR {
            override val extension: String = "tar.gz"
            override fun createDecompressor(file: File): Decompressor = Decompressor.Tar(file)
        };

        protected abstract val extension: String
        protected abstract fun createDecompressor(file: File): Decompressor

        companion object {
            @Throws(IOException::class)
            fun unarchive(archivePath: File, dst: File) {
                val unarchiver = values().find { archivePath.name.endsWith(it.extension) }
                    ?: error("Unexpected archive type: $archivePath")
                unarchiver.createDecompressor(archivePath).extract(dst)
            }
        }
    }

    private val cliUrl: URL
        get() {
            return when {
                SystemInfo.isMac -> URL("https://github.com/flagship-io/flagship/releases/download/v$cliVersion/flagship_${cliVersion}_darwin_all.tar.gz")
                SystemInfo.isLinux -> URL("https://github.com/flagship-io/flagship/releases/download/v$cliVersion/flagship_${cliVersion}_linux_amd64.tar.gz")
                SystemInfo.isWindows -> {
                    if (CpuArch.isIntel64()) {
                        URL("https://github.com/flagship-io/flagship/releases/download/v$cliVersion/flagship_${cliVersion}_windows_amd64.tar.gz")
                    } else {
                        URL("https://github.com/flagship-io/flagship/releases/download/v$cliVersion/flagship_${cliVersion}_windows_386.tar.gz")
                    }
                }

                else -> return URL("")
            }
        }

    sealed class DownloadResult {
        class Ok(val crDir: File) : DownloadResult()
        object Failed : DownloadResult()
    }
}
