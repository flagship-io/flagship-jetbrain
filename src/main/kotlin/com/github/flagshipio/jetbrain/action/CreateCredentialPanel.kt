package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.dataClass.Credential
import com.github.flagshipio.jetbrain.store.CredentialStore
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserFactory
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets

class CreateCredentialPanel : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project ?: return
        val credentialStore = CredentialStore(project)
        val credentialName = credentialNameInput ?: return
        val clientID: String;
        val clientSecret: String;
        val accountID: String;
        val accountEnvironmentID: String;
        //var credential: Credential


        val result = Messages.showOkCancelDialog(
            "Choose",
            "Choose Method",
            "Load Configuration From File",
            "Create Configuration With Inputs",
            Messages.getInformationIcon()
        )

        if (result == 0) {

            val fileChooserDialog = FileChooserFactory.getInstance().createFileChooser(
                createFileChooserDescriptor(),
                project,
                null
            )
            val selectedFiles = fileChooserDialog.choose(project)
            if (selectedFiles.isEmpty()) {
                return
            }
            val content = readYmlFileContent(selectedFiles[0])
            if (content != null) {
                showYmlContentDialog(content, selectedFiles[0].name)

            } else {
                Messages.showErrorDialog("Failed to read YML file content.", "Error")
            }
            for (selectedFile in selectedFiles) {
                val selectedIoFile = File(selectedFile.path)

                if (selectedIoFile.exists() && !selectedIoFile.isDirectory()) {
                    openFileInEditor(project, selectedFile)
                }
            }
            return
        }

        clientID = clientIDInput ?: return
        clientSecret = clientSecretInput ?: return
        accountID = accountIDInput ?: return
        accountEnvironmentID = accountEnvironmentIDInput ?: return

        val credential = Credential(credentialName, clientID, clientSecret, accountID, accountEnvironmentID)
        if (credentialStore.exists(credential)) {
            Messages.showMessageDialog("Credential not saved", "Status", Messages.getErrorIcon())
            return
        }

        credentialStore.saveCredential(credential)
        val message = "$clientID\n$clientSecret\n$accountID\n$accountEnvironmentID"
        Messages.showMessageDialog(message, "Credential Saved !", Messages.getInformationIcon())
    }

    private val credentialNameInput: String?
        get() = Messages.showInputDialog(
            "Enter your Credential name:",
            "Step 1 - Client ID",
            Messages.getQuestionIcon()
        )
    private val clientIDInput: String?
        get() = Messages.showInputDialog("Enter your Client ID:", "Step 2 - Client ID", Messages.getQuestionIcon())
    private val clientSecretInput: String?
        get() = Messages.showInputDialog(
            "Enter your Client secret:",
            "Step 3 - Client Secret",
            Messages.getQuestionIcon()
        )
    private val accountIDInput: String?
        get() {
            return Messages.showInputDialog(
                "Enter your Account ID:", "Step 4 - Account ID", Messages.getQuestionIcon()
            )
        }
    private val accountEnvironmentIDInput: String?
        get() = Messages.showInputDialog(
            "Enter your Account environment ID:",
            "Step 5 - Account Environment ID",
            Messages.getQuestionIcon()
        )


    private fun openFileInEditor(project: Project, virtualFile: VirtualFile) {
        FileEditorManager.getInstance(project).openFile(virtualFile, true)
    }

    private fun createFileChooserDescriptor(): FileChooserDescriptor {
        val descriptor = FileChooserDescriptor(true, false, false, false, false, false)
        descriptor.title = "Open File"
        descriptor.description = "Select a file to open in IntelliJ IDEA"
        descriptor.withFileFilter {
            it.isDirectory || it.name.lowercase().endsWith(".yml") || it.name.lowercase()
                .endsWith(".yaml") || it.name.lowercase().endsWith(".json")
        }
        return descriptor
    }

    private fun readYmlFileContent(file: VirtualFile): String? {
        try {
            val inputStream = file.inputStream
            val content = IOUtils.toString(inputStream, StandardCharsets.UTF_8)
            inputStream.close()
            return content
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun showYmlContentDialog(content: String, fileName: String) {
        Messages.showInfoMessage(content, "YML File: $fileName")
    }

}