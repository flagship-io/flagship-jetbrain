package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.messaging.DefaultMessageBusService
import com.github.flagshipio.jetbrain.store.FlagStore
import com.github.flagshipio.jetbrain.toolWindow.quickLink.LinkPanel
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border


class FlagPanel(project: Project) : JPanel() {
    private val messageBus = project.service<DefaultMessageBusService>()
    private val splitter = OnePixelSplitter(true, "LDSplitterProportion", .25f)
    private val flagStore = FlagStore(project)

    private val listFlagPanel = FlagListPanel(project, messageBus)
    private val manageFlagPanel = ManageFlagPanel(project, flagStore, listFlagPanel)

    init {

        val manageFlagTitle: Border = BorderFactory.createTitledBorder("Manage Feature flags")
        val flagsTitle: Border = BorderFactory.createTitledBorder("List Feature flags")

        manageFlagPanel.border = manageFlagTitle
        listFlagPanel.border = flagsTitle
        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = manageFlagPanel
            secondComponent = listFlagPanel

        }
        add(splitter, BorderLayout.CENTER)
    }

    fun getFlagListPanel(): FlagListPanel {
        return listFlagPanel
    }

    fun getManageFlagPanel(): ManageFlagPanel {
        return manageFlagPanel
    }

}
