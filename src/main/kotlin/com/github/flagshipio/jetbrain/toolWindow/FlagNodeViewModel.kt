package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.dataClass.Feature

class FlagNodeViewModel(
    val flag: Feature,
) {
    val flagDescription = flag.description
    val hasDescription = flag.description != ""
    val flagLabel = flag.name
    val flagId = flag.id
    val defaultValue = flag.defaultValue
    val flagType = flag.type
    val flagSource = flag.source
}
