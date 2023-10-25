package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.TargetingKey
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "TargetingKeyDataService", storages = [Storage("TargetingKeyData.xml")])
class TargetingKeyDataService : PersistentStateComponent<List<TargetingKey>> {
    private var targetingKeyList: List<TargetingKey> = emptyList()

    override fun getState(): List<TargetingKey> {
        return targetingKeyList
    }

    override fun loadState(state: List<TargetingKey>) {
        targetingKeyList = state
    }

    fun saveTargetingKey(targetingKey: TargetingKey) {
        val newTargetingKeys = state.plus(targetingKey)
        loadState(newTargetingKeys)
    }

    fun editTargetingKey(targetingKey: TargetingKey, newTargetingKey: TargetingKey) {
        val oldTargetingKeys = state.minus(targetingKey)
        val newTargetingKeys = oldTargetingKeys.plus(newTargetingKey)

        loadState(newTargetingKeys)
    }

    fun deleteTargetingKey(targetingKey: TargetingKey) {
        val newTargetingKeys = state.minus(targetingKey)
        loadState(newTargetingKeys)
    }
}
