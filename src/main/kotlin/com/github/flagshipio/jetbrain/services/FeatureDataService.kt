package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.Feature
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "FeatureDataService", storages = [Storage("featureData.xml")])
class FeatureDataService : PersistentStateComponent<List<Feature>> {
    private var featureList: List<Feature> = emptyList()

    override fun getState(): List<Feature> {
        return featureList
    }

    override fun loadState(state: List<Feature>) {
        featureList = state
    }

    fun saveFeatures(features: List<Feature>) {
        featureList = features
    }

    fun getFeatures(): List<Feature> {
        return featureList
    }
}
