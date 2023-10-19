package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.store.FlagStore
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border


class FlagPanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "FlagSplitter", .05f)
    private val flagStore = FlagStore(project)
    private val listFlagTitle = "List Feature flag"

    private val listFlagPanel = FlagListPanel(project)
    private val manageFlagPanel = ManageFlagPanel(project, flagStore)

    init {

        val manageFlagBorder: Border = BorderFactory.createTitledBorder("Manage Feature flags")
        val listFlagBorder: Border = BorderFactory.createTitledBorder(listFlagTitle + " ("+ flagStore.getFlags().count() +" Flags)")

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

    fun updateListFlagBorder() {
        this.listFlagPanel.border = BorderFactory.createTitledBorder(listFlagTitle + " ("+ flagStore.getFlags().count() +" Flags)")
    }

    fun getFlagListPanel(): FlagListPanel {
        return listFlagPanel
    }

    fun getManageFlagPanel(): ManageFlagPanel {
        return manageFlagPanel
    }
}
