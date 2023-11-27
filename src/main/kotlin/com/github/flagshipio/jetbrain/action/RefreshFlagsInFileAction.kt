package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.store.*
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class RefreshFlagsInFileAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.RefreshFlagsInFileAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        if (event.project != null) {
            val flagsInFileStore = event.project?.let { FlagsInFileStore(it) }
            val selectedEditor = event.getData(CommonDataKeys.VIRTUAL_FILE);
            if (selectedEditor != null) {
                flagsInFileStore?.refreshFlagInFile(selectedEditor.path)
                ActionHelpers.getListFlagsInFilePanel(event.project!!).updateNodeInfo()
                return
            }
            if (flagsInFileStore != null) {
                event.project!!.basePath?.let { flagsInFileStore.refreshFlagInFile(it) }
            }
            ActionHelpers.getListFlagsInFilePanel(event.project!!).updateNodeInfo()

        }
    }
}
