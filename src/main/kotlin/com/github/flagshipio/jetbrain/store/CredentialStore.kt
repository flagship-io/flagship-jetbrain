package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.dataClass.Credential
import com.github.flagshipio.jetbrain.services.CredentialDataService
import com.intellij.openapi.project.Project

class CredentialStore(project: Project) {

    private var credentialDataService: CredentialDataService

    init {
        credentialDataService = project.getService(CredentialDataService::class.java)
    }

    fun saveCredential(credential: Credential) {
        credentialDataService.saveCredential(credential)
    }

    fun getCredentials(): List<Credential> {
        return credentialDataService.getCredentials()
    }

    fun getCredentialByName(credentialName: String): Credential? {
        return credentialDataService.getCredentialByName(credentialName)
    }

    fun exists(credential: Credential): Boolean {
        return credentialDataService.isCredentialExists(credential)
    }
}