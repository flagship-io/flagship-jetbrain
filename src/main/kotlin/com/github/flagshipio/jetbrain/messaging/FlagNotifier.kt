package com.github.flagshipio.jetbrain.messaging

interface FlagNotifier {
    fun notify(isConfigured: Boolean, flag: String = "", rebuild: Boolean = false)
    fun reinit()
}
