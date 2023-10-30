package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation.modification

import com.github.flagshipio.jetbrain.dataClass.Modification
import com.github.flagshipio.jetbrain.dataClass.Variation

class ModificationNodeViewModel(
    private val modification: Modification?,
) {
    val modificationType = modification?.type
    val modificationValue = modification?.value
}
