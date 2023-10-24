package com.github.flagshipio.jetbrain.services

import com.github.flagshipio.jetbrain.dataClass.Goal
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "GoalDataService", storages = [Storage("GoalData.xml")])
class GoalDataService : PersistentStateComponent<List<Goal>> {
    private var goalList: List<Goal> = emptyList()

    override fun getState(): List<Goal> {
        return goalList
    }

    override fun loadState(state: List<Goal>) {
        goalList = state
    }

    fun saveGoal(goal: Goal) {
        val newGoals = state.plus(goal)
        loadState(newGoals)
    }

    fun editGoal(goal: Goal, newGoal: Goal) {
        val oldGoals = state.minus(goal)
        val newGoals = oldGoals.plus(newGoal)

        loadState(newGoals)
    }

    fun deleteGoal(goal: Goal) {
        val newGoals = state.minus(goal)
        loadState(newGoals)
    }
}
