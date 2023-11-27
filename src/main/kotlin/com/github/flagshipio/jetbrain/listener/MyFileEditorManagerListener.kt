package com.github.flagshipio.jetbrain.listener

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.FlagsInFileStore
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MyFileEditorManagerListener : FileEditorManagerListener {

    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        //printSelectedFilePath(source)
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        // Handle file closure if needed
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun selectionChanged(event: FileEditorManagerEvent) {
        GlobalScope.async {
            refreshFlagsInFile(event.manager)
        }
    }

    private fun refreshFlagsInFile(fileEditorManager: FileEditorManager) {
        val flagsInFileStore = FlagsInFileStore(fileEditorManager.project)
        val selectedEditor = fileEditorManager.selectedEditor
        if (selectedEditor != null) {
            val file = selectedEditor.file
            if (file != null) {
                flagsInFileStore.refreshFlagInFile(file.path)
                ActionHelpers.getListFlagsInFilePanel(fileEditorManager.project).updateNodeInfo()
                return
            }
        }
        fileEditorManager.project.basePath?.let { flagsInFileStore.refreshFlagInFile(it) }
        ActionHelpers.getListFlagsInFilePanel(fileEditorManager.project).updateNodeInfo()
    }
}