package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationNodeParent
import com.github.flagshipio.jetbrain.toolWindow.configuration.NAME_PREFIX
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import javax.swing.BorderFactory
import javax.swing.tree.DefaultMutableTreeNode

class SelectConfigurationAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.SelectConfigurationAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val configurationStore = ConfigurationStore(project)
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListConfigurationTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is ConfigurationNodeParent) {
                val configurationNodeParent = selectedNode.userObject as ConfigurationNodeParent
                val isChangedConfiguration = configurationStore.useConfiguration(configurationNodeParent.configuration)
                if (isChangedConfiguration) {
                    val newBorderTitle = BorderFactory.createTitledBorder("List Configuration - Current Configuration ${configurationNodeParent.configuration.name}")
                    ActionHelpers.getListConfigurationPanel(project).border = newBorderTitle
                    ActionHelpers.getListFlagPanel(project).updateNodeInfo()
                    Messages.showMessageDialog("Configuration selected", "Status", Messages.getInformationIcon())
                }
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
