package com.github.flagshipio.jetbrain.toolWindow.targetingKey

import com.github.flagshipio.jetbrain.dataClass.TargetingKey

class TargetingKeyNodeViewModel(
    val targetingKey: TargetingKey,
) {
    val targetingKeyName = targetingKey.name
    val targetingKeyId = targetingKey.id
    val targetingKeyType = targetingKey.type
    val targetingKeyDescription = targetingKey.description
}
