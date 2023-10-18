package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.Flag
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "FlagDataService", storages = [Storage("FlagData.xml")])
class FlagDataService : PersistentStateComponent<List<Flag>> {
    private var flagList: List<Flag> = emptyList()

    override fun getState(): List<Flag> {
        return flagList
    }

    override fun loadState(state: List<Flag>) {
        flagList = state
    }

    fun saveFlag(flag: Flag) {
        val newFlags = state.plus(flag)
        loadState(newFlags)
    }

    fun editFlag(flag: Flag, newFlag: Flag) {
        val oldFlags = state.minus(flag)
        val newFlags = oldFlags.plus(newFlag)

        loadState(newFlags)
    }

    fun deleteFlag(flag: Flag) {
        val newFlags = state.minus(flag)
        loadState(newFlags)
    }
}
