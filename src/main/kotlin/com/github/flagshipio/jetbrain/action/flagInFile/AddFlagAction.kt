package com.github.flagshipio.jetbrain.action.flagInFile

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.store.FlagStore
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagNodeParent
import com.github.flagshipio.jetbrain.toolWindow.flagsInFile.FlagInFileNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode

val KEY_PREFIX = "Key:"

class AddFlagAction : AnAction() {

    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.AddFlagAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val flagStore = FlagStore(project)
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagInFileTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is FlagInFileNodeParent) {
                val flagNodeParent = selectedNode.userObject as FlagInFileNodeParent
                val featureFlag = Flag(
                    null,
                    flagNodeParent.flagAnalyzed.flagKey,
                    flagNodeParent.flagAnalyzed.flagType,
                    null,
                    flagNodeParent.flagAnalyzed.flagDefaultValue?.replace("\"", "")
                )
                flagStore.saveFlag(featureFlag)
                ActionHelpers.getListFlagPanel(project).updateNodeInfo()
                return
            } else {
                selectedNode = selectedNode.parent as? DefaultMutableTreeNode
            }
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagInFileTreeNode(project!!)
        val isFlagInFileParentNode = selectedNode!!.userObject is FlagInFileNodeParent
        val hasKeyPrefix = selectedNode.toString().startsWith(KEY_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasKeyPrefix || isFlagInFileParentNode)
    }
}