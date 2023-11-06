package com.github.flagshipio.jetbrain.listener

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.FlagsInFileStore
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile

class MyFileEditorManagerListener: FileEditorManagerListener {

    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        //printSelectedFilePath(source)
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        // Handle file closure if needed
    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        refreshFlagsInFile(event.manager)
    }

    private fun refreshFlagsInFile(fileEditorManager: FileEditorManager) {
        val selectedEditor = fileEditorManager.selectedEditor
        if (selectedEditor != null) {
            val file = selectedEditor.file
            if (file != null) {
                val flagsInFileStore = FlagsInFileStore(fileEditorManager.project)
                flagsInFileStore.refreshFlag(file.path)
                ActionHelpers.getListFlagsInFilePanel(fileEditorManager.project).updateNodeInfo()
            }
        }
    }
}