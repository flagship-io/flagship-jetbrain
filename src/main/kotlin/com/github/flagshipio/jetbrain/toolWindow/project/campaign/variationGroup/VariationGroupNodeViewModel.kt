package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup

import com.github.flagshipio.jetbrain.dataClass.Campaign
import com.github.flagshipio.jetbrain.dataClass.Project
import com.github.flagshipio.jetbrain.dataClass.VariationGroup

class VariationGroupNodeViewModel(
    val variationGroup: VariationGroup,
) {
    val vgID = variationGroup.id
    val vgName = variationGroup.name
    val vgVariations = variationGroup.variations
    val vgTargeting = variationGroup.targeting
}
