package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation

import com.github.flagshipio.jetbrain.dataClass.Variation

class VariationNodeViewModel(
    val variation: Variation,
) {
    val variationID = variation.id
    val variationName = variation.name
    val variationReference = variation.reference
    val variationAllocation = variation.allocation
    val variationModification = variation.modifications
}
