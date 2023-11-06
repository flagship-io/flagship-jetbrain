package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.cli.Cli
import com.github.flagshipio.jetbrain.store.*
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory


class MyToolWindowFactory : ToolWindowFactory {

    init {
        val cli = Cli()
        cli.downloadCli()

        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val flagStore = FlagStore(project)
        val configurationStore = ConfigurationStore(project)
        val goalStore = GoalStore(project)
        val targetingKeyStore = TargetingKeyStore(project)
        val projectStore = ProjectStore(project)
        val flagsInFileStore = FlagsInFileStore(project)

        configurationStore.refreshConfiguration()
        projectStore.refreshProject()
        flagStore.refreshFlag()
        goalStore.refreshGoal()
        targetingKeyStore.refreshTargetingKey()

        val applicationToolWindow = ApplicationToolWindow.getInstance(project)

        applicationToolWindow.initializeConfigurationPanel(toolWindow)
        applicationToolWindow.initializeProjectPanel(toolWindow)
        applicationToolWindow.initializeFlagPanel(toolWindow)
        applicationToolWindow.initializeFlagsInFilePanel(toolWindow)
        applicationToolWindow.initializeTargetingKeyPanel(toolWindow)
        applicationToolWindow.initializeGoalPanel(toolWindow)

        val currentOpenedFile = getCurrentEditorFilePath(project)

        if (currentOpenedFile != null) {
            flagsInFileStore.refreshFlag(currentOpenedFile)
        }
    }

    override fun shouldBeAvailable(project: Project) = true

}

fun getCurrentEditorFilePath(project: Project): String? {
    val fileEditorManager = FileEditorManager.getInstance(project)
    val selectedEditors = fileEditorManager.selectedEditors

    if (selectedEditors.isNotEmpty()) {
        val selectedEditor = selectedEditors[0] // Get the first selected editor

        // Check if the editor is associated with a file
        val file = selectedEditor.file
        if (file != null) {
            return file.path
        }
    }
    return null
}
