package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CheckCLI
import com.github.flagshipio.jetbrain.dataClass.Feature
import com.github.flagshipio.jetbrain.services.FeatureDataService
import com.intellij.openapi.project.Project

class FeatureStore(project: Project) {

    private var featureDataService: FeatureDataService
    private val checkCLI = CheckCLI()

    init {
        featureDataService = project.getService(FeatureDataService::class.java)
    }

    fun refreshFeatureFlag(): List<Feature>? {
        val features = checkCLI.listFlagCli()

        if (features != null) {
            featureDataService.saveFeatures(features)
        }
        return features

    }

    fun saveFeatureFlag(project: Project) {
        val features = refreshFeatureFlag()
        // Save or retrieve features from the service
    }

    fun getFeatureFlag(project: Project): List<Feature> {
        return featureDataService.getFeatures()
    }
}