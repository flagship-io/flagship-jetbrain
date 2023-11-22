package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.store.FlagStore
import com.github.flagshipio.jetbrain.store.GoalStore
import com.github.flagshipio.jetbrain.store.ProjectStore
import com.github.flagshipio.jetbrain.store.TargetingKeyStore
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.ui.Messages

class SyncResourceAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.SyncResourceAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val flagStore = FlagStore(project)
        val projectStore = ProjectStore(project)
        val targetingKeyStore = TargetingKeyStore(project)
        val goalStore = GoalStore(project)

        ProgressManager.getInstance().runProcessWithProgressSynchronously(
            Runnable {
                val progressIndicator: ProgressIndicator? = ProgressManager.getInstance().progressIndicator
                progressIndicator?.fraction = 0.1
                progressIndicator?.text = "Loading projects and campaigns..."
                projectStore.refreshProject()
                progressIndicator?.fraction = 0.5
                progressIndicator?.text = "Loading flags..."
                flagStore.refreshFlag()
                progressIndicator?.fraction = 0.7
                progressIndicator?.text = "Loading targeting keys..."
                targetingKeyStore.refreshTargetingKey()
                progressIndicator?.fraction = 0.9
                progressIndicator?.text = "Loading goals..."
                goalStore.refreshGoal()
                progressIndicator?.fraction = 1.0
            },
            "Loading Flagship Resources...",
            false,
            project
        )

        ActionHelpers.getListProjectPanel(project).updateNodeInfo()

        ActionHelpers.getListFlagPanel(project).updateNodeInfo()

        ActionHelpers.getListTargetingKeyPanel(project).updateNodeInfo()

        ActionHelpers.getListGoalPanel(project).updateNodeInfo()

        ActionHelpers.getListConfigurationPanel(project).updateNodeInfo()

        Messages.showMessageDialog("Synchronized successfully", "Status", Messages.getInformationIcon())

        return
    }
}
