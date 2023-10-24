package com.github.flagshipio.jetbrain.toolWindow.goal

import com.github.flagshipio.jetbrain.dataClass.Goal

class GoalNodeViewModel(
    val goal: Goal,
) {
    val goalLabel = goal.label
    val goalId = goal.id
    val goalType = goal.type
    val goalOperator = goal.operator
    val goalValue = goal.value

}
