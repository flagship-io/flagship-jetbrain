package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.Goal
import com.github.flagshipio.jetbrain.services.GoalDataService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class GoalStore(project: Project) {

    private var goalDataService: GoalDataService
    private val cliCommand = CliCommand()

    init {
        goalDataService = project.getService(GoalDataService::class.java)
    }

    fun refreshGoal(): List<Goal>? {
        val goals = cliCommand.listGoalCli()
        if (goals != null) {
            goalDataService.loadState(goals)
        }
        return goals
    }

    fun saveGoal(goal: Goal): Goal? {
        val cliResponse = cliCommand.addGoalCli(goal)
        if (cliResponse != null) {
            goalDataService.saveGoal(cliResponse)
            Messages.showMessageDialog("Goal saved", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun editGoal(goal: Goal, newGoal: Goal): Goal? {
        val cliResponse = goal.id?.let { cliCommand.editGoalCli(it, newGoal) }
        if (cliResponse != null) {
            goalDataService.editGoal(goal, cliResponse)
            Messages.showMessageDialog("Goal edited", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun deleteGoal(goal: Goal): String? {
        val cliResponse = goal.id?.let { cliCommand.deleteGoalCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted", true)) {
                goalDataService.deleteGoal(goal)
                Messages.showMessageDialog("Goal deleted", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun getGoals(): List<Goal> {
        return goalDataService.state
    }
}