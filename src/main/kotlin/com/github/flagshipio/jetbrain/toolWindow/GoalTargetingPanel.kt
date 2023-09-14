package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.messaging.DefaultMessageBusService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter

import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border


class GoalTargetingPanel(project: Project) : JPanel() {
    private val messageBus = project.service<DefaultMessageBusService>()
    private val splitter = OnePixelSplitter(true, "LDSplitterProportion", .25f)
    private val linkPanel = LinkPanel(project)
    private val flagPanel = FlagPanel(project, messageBus)

    init {
        val goalTitle: Border = BorderFactory.createTitledBorder("Goal")
        val targetingTitle: Border = BorderFactory.createTitledBorder("Targeting")

        linkPanel.border = goalTitle
        flagPanel.border = targetingTitle
        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(false)
            firstComponent = linkPanel
            secondComponent = flagPanel

        }
        add(splitter, BorderLayout.CENTER)
    }

    fun getFlagPanel(): FlagPanel {
        return flagPanel
    }

}
