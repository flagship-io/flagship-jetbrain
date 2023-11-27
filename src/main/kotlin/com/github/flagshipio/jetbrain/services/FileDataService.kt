package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.FileAnalyzed
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "FileDataService", storages = [Storage("FileData.xml")])
class FileDataService : PersistentStateComponent<List<FileAnalyzed>> {
    private var flagList: List<FileAnalyzed> = emptyList()

    override fun getState(): List<FileAnalyzed> {
        return flagList
    }

    override fun loadState(state: List<FileAnalyzed>) {
        flagList = state
    }
}
