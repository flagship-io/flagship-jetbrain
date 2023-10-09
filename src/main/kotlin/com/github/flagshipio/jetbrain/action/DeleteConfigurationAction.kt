package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.toolWindow.ConfigurationNodeParent
import com.github.flagshipio.jetbrain.toolWindow.FlagNodeParent
import com.github.flagshipio.jetbrain.toolWindow.NAME_PREFIX
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode

class DeleteConfigurationAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.DeleteConfigurationAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListConfigurationTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is ConfigurationNodeParent) {
                val configurationNodeParent = selectedNode.userObject as ConfigurationNodeParent
                println(configurationNodeParent.name_)
                val selection = StringSelection(configurationNodeParent.name_)
                val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                return clipboard.setContents(selection, selection)
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
