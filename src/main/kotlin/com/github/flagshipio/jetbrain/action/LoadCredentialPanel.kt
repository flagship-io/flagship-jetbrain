package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.store.CredentialStore
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class LoadCredentialPanel : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project ?: return
        val credentialStore = CredentialStore(project)
        val credentialNames = ArrayList<String>()

        val credentials = credentialStore.getCredentials()
        credentials.forEach { credential ->
            credentialNames.add(credential.name)
        }

        if (credentialNames.isEmpty()) {
            credentialNames.add("No credentials found.")
        }
        println(credentials.toString())
        val credentialNamesTyped: Array<out String?> = credentialNames.toTypedArray()
        val credentialName = Messages.showEditableChooseDialog(
            "Select a file to open:",
            "File List",
            Messages.getInformationIcon(),
            credentialNamesTyped,
            credentialNames[0],
            null
        )
        if (credentialName != null) {
            val credentialSelected = credentialStore.getCredentialByName(credentialName)
            if (credentialSelected != null) {
                val message = "$credentialSelected\n"
                Messages.showMessageDialog(message, "Credential Selected !", Messages.getInformationIcon())
            }
        }

    }
}