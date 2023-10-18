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
    private val splitter = OnePixelSplitter(true, "LDSplitterProportion", .25f)
    private val flagStore = FlagStore(project)

    private val listFlagPanel = FlagListPanel(project)
    private val manageFlagPanel = ManageFlagPanel(project, flagStore, listFlagPanel)

    init {
        val listFlagTitle = "List Feature flag"
        val manageFlagBorder: Border = BorderFactory.createTitledBorder("Manage Feature flags")
        val listFlagBorder: Border = BorderFactory.createTitledBorder(listFlagTitle)

        manageFlagPanel.border = manageFlagBorder
        listFlagPanel.border = listFlagBorder

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
