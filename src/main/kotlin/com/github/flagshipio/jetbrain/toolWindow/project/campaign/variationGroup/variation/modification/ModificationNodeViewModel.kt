package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation.modification

import com.github.flagshipio.jetbrain.dataClass.Modification

class ModificationNodeViewModel(
    private val modification: Modification?,
) {
    val modificationType = modification?.type
    val modificationValue = modification?.value
}
