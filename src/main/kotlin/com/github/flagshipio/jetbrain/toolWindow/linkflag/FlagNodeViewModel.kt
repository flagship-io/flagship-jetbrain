package com.github.flagshipio.jetbrain.toolWindow.linkflag

import com.github.flagshipio.jetbrain.dataClass.Flag

class FlagNodeViewModel(
    val flag: Flag,
) {
    val flagDescription = flag.description
    val hasDescription = flag.description != ""
    val flagLabel = flag.name
    val flagId = flag.id
    val defaultValue = flag.defaultValue
    val flagType = flag.type
    val flagSource = flag.source
}
