package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.dataClass.Configuration

class ConfigurationNodeViewModel(
    val configuration: Configuration,
) {
    val configName = configuration.name
    val configClientID = "xxxx" + (configuration.clientID?.takeLast(8) ?: "")
    val configClientSecret = "xxxx" + (configuration.clientSecret?.takeLast(8) ?: "")
    val configAccountID = configuration.accountID
    val configAccountEnvID = configuration.accountEnvironmentID
}
