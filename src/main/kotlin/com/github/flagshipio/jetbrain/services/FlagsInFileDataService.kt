package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.FileAnalyzed
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.dataClass.FlagAnalyzed
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "FlagsInFileDataService", storages = [Storage("FlagsInFileData.xml")])
class FlagsInFileDataService : PersistentStateComponent<List<FlagAnalyzed>> {
    private var flagList: List<FlagAnalyzed> = emptyList()

    override fun getState(): List<FlagAnalyzed> {
        return flagList
    }

    override fun loadState(state: List<FlagAnalyzed>) {
        flagList = state
    }
}
