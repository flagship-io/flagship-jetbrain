package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.dataClass.Configuration

class ConfigurationNodeViewModel(
    val configuration: Configuration,
) {
    val configName = configuration.name
    val configClientID = configuration.clientID
    val configClientSecret = configuration.clientSecret
    val configAccountID = configuration.accountID
    val configAccountEnvID = configuration.accountEnvironmentID
}
