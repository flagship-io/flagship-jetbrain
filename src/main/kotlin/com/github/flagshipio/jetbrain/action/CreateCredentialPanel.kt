package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.dataClass.Credential
import com.github.flagshipio.jetbrain.store.CredentialStore
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.ui.Messages

class CreateCredentialPanel : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project ?: return
        val credentialStore = CredentialStore(project)

        val credentialName = credentialNameInput?: return
        val clientID = clientIDInput ?: return
        val clientSecret = clientSecretInput ?: return
        val accountID = accountIDInput ?: return
        val accountEnvironmentID = accountEnvironmentIDInput ?: return

        val credential = Credential(credentialName, clientID, clientSecret, accountID, accountEnvironmentID)
        if(credentialStore.exists(credential)){
            Messages.showMessageDialog("Credential not saved", "Status", Messages.getErrorIcon())
            return
        }

        credentialStore.saveCredential(credential)
        val message = "$clientID\n$clientSecret\n$accountID\n$accountEnvironmentID"
        Messages.showMessageDialog(message, "Credential Saved !", Messages.getInformationIcon())
    }


    private val credentialNameInput: String?
        get() = Messages.showInputDialog("Enter your Credential name:", "Step 1 - Client ID", Messages.getQuestionIcon())
    private val clientIDInput: String?
        get() = Messages.showInputDialog("Enter your Client ID:", "Step 2 - Client ID", Messages.getQuestionIcon())
    private val clientSecretInput: String?
        get() = Messages.showInputDialog("Enter your Client secret:", "Step 3 - Client Secret", Messages.getQuestionIcon())
    private val accountIDInput: String?
        get() {
            return Messages.showInputDialog(
                "Enter your Account ID:", "Step 4 - Account ID", Messages.getQuestionIcon()
            )
        }
    private val accountEnvironmentIDInput: String?
        get() = Messages.showInputDialog("Enter your Account environment ID:", "Step 5 - Account Environment ID", Messages.getQuestionIcon())

}