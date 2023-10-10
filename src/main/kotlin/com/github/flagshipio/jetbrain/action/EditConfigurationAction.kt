package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.dialog.EditConfigurationDialog
import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationNodeParent
import com.github.flagshipio.jetbrain.toolWindow.configuration.NAME_PREFIX
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import javax.swing.tree.DefaultMutableTreeNode


class EditConfigurationAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.EditConfigurationAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val configurationStore = ConfigurationStore(project)
        //val newConfiguration = Configuration("newName", "ci", "cs", "a", "e")
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListConfigurationTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is ConfigurationNodeParent) {
                val configurationNodeParent = selectedNode.userObject as ConfigurationNodeParent
                println(configurationNodeParent.name_)
                println(configurationNodeParent.configuration)

                val dialog = EditConfigurationDialog(project)
                dialog.setNameText(configurationNodeParent.configuration.name!!)
                dialog.setClientIDText(configurationNodeParent.configuration.clientID!!)
                dialog.setClientSecretText(configurationNodeParent.configuration.clientSecret!!)
                dialog.setAccountIDText(configurationNodeParent.configuration.accountID!!)
                dialog.setAccountEnvironmentIDText(configurationNodeParent.configuration.accountEnvironmentID!!)

                dialog.show()

                if (dialog.exitCode == DialogWrapper.CANCEL_EXIT_CODE) {
                    return
                }
                val newConfiguration = Configuration(dialog.name, dialog.clientID, dialog.clientSecret, dialog.accountID, dialog.accountEnvironmentID)

                configurationStore.editConfiguration(configurationNodeParent.configuration, newConfiguration)
                ActionHelpers.getListConfigurationPanel(project).updateNodeInfo()
                Messages.showMessageDialog("Configuration edited", "Status", Messages.getInformationIcon())
                return
            } else {
                selectedNode = selectedNode.parent as? DefaultMutableTreeNode
            }
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListConfigurationTreeNode(project!!)
        val isConfigurationParentNode = selectedNode!!.userObject is ConfigurationNodeParent
        val hasNamePrefix = selectedNode.toString().startsWith(NAME_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasNamePrefix || isConfigurationParentNode)
    }
}
