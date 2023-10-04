package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CheckCLI
import com.github.flagshipio.jetbrain.dataClass.Feature
import com.github.flagshipio.jetbrain.services.FeatureDataService
import com.intellij.openapi.project.Project

class FeatureStore(project: Project) {

    private var featureDataService: FeatureDataService

    init {
        featureDataService = project.getService(FeatureDataService::class.java)
    }

    fun refreshFeatureFlag(project: Project): List<Feature>? {
        val checkCLI = CheckCLI(project)

        return checkCLI.listFlagCli(project)

    }

    fun saveFeatureFlag(project: Project) {
        val features = refreshFeatureFlag(project)
        // Save or retrieve features from the service

        if (features != null) {
            featureDataService.saveFeatures(features)
        }
    }

    fun getFeatureFlag(project: Project): List<Feature> {
        return featureDataService.getFeatures()
    }
}