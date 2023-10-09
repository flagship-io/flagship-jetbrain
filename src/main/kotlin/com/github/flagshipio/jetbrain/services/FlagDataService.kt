package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.Flag
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "FlagDataService", storages = [Storage("flagData.xml")])
class FlagDataService : PersistentStateComponent<List<Flag>> {
    private var flagList: List<Flag> = emptyList()

    override fun getState(): List<Flag> {
        return flagList
    }

    override fun loadState(state: List<Flag>) {
        flagList = state
    }
}
