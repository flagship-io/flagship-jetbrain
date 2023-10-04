package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State

@Service
@State(name = "ConfigurationDataService")
class ConfigurationDataService : PersistentStateComponent<List<Configuration>> {
    private var configurationList: List<Configuration> = emptyList()

    override fun getState(): List<Configuration> {
        return configurationList
    }

    override fun loadState(state: List<Configuration>) {
        configurationList = state
    }

    fun saveConfigurations(features: List<Configuration>) {
        configurationList = features
    }

    fun getConfigurations(): List<Configuration> {
        return configurationList
    }
}
