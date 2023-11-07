package com.github.flagshipio.jetbrain.action.flagInFile

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.flagsInFile.FlagInFileNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import javax.swing.tree.DefaultMutableTreeNode

class GoToFlagFileAction : AnAction() {

    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.GoToFlagFileAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagInFileTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is FlagInFileNodeParent) {
                val flagNodeParent = selectedNode.userObject as FlagInFileNodeParent
                //println(flagNodeParent.flagAnalyzed.flagFile)
                val selectedIoFile = flagNodeParent.flagAnalyzed.flagFile?.let { File(it) }

                if (selectedIoFile != null) {
                    if (selectedIoFile.exists() && !selectedIoFile.isDirectory()) {
                        val filePath: String = selectedIoFile.absolutePath
                        val virtualFile = LocalFileSystem.getInstance().findFileByPath(filePath)
                        if (virtualFile != null) {
                            openFileInEditor(project, virtualFile)
                        }
                    }
                }
                return
            } else {
                selectedNode = selectedNode.parent as? DefaultMutableTreeNode
            }
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val KEY_PREFIX = "Key:"
        val project = e.project
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagInFileTreeNode(project!!)
        val isFlagInFileParentNode = selectedNode!!.userObject is FlagInFileNodeParent
        val hasKeyPrefix = selectedNode.toString().startsWith(KEY_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasKeyPrefix || isFlagInFileParentNode)
    }
}

private fun openFileInEditor(project: Project, virtualFile: VirtualFile) {
    FileEditorManager.getInstance(project).openFile(virtualFile, true)
}