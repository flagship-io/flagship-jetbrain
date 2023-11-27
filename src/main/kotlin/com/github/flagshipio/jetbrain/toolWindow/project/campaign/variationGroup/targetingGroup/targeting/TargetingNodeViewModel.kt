package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.targetingGroup.targeting

import com.github.flagshipio.jetbrain.dataClass.Targetings

class TargetingNodeViewModel(
    val targeting: Targetings,
) {
    val targetingKey = targeting.key
    val targetingOperator = targeting.operator
    val targetingValue = targeting.value
}
