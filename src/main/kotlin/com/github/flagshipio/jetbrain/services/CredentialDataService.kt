package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.Credential
import com.github.flagshipio.jetbrain.dataClass.Feature
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "CredentialDataService", storages = [Storage("CredentialData.xml")])
class CredentialDataService : PersistentStateComponent<List<Credential>> {
    private var credentialList: List<Credential> = emptyList()

    override fun getState(): List<Credential> {
        return credentialList
    }

    override fun loadState(state: List<Credential>) {
        credentialList = state
    }

    fun saveCredentials(features: List<Credential>) {
        credentialList = features
    }

    fun saveCredential(credential: Credential) {
        val newCredentials = credentialList.plus(credential)
        saveCredentials(newCredentials)
    }

    fun getCredentials(): List<Credential> {
        return credentialList
    }

    fun getCredentialByName(name: String): Credential? {
        credentialList.forEach{
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    fun isCredentialExists(credential: Credential): Boolean {
        var nameExist = false
        credentialList.forEach {
            if (it.name == credential.name) {
                nameExist = true
            }
        }
        return nameExist
    }

}
