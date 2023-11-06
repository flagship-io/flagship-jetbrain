package com.github.flagshipio.jetbrain.toolWindow.flagsInFile

import com.github.flagshipio.jetbrain.store.FlagsInFileStore
import com.github.flagshipio.jetbrain.store.GoalStore
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border

class FlagsInFilePanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "FlagsInFileSplitter", .05f)
    private val flagsInFileStore = FlagsInFileStore(project)
    private val goalStore = GoalStore(project)
    private val listFlagsInFileTitle = "Flags In File"

    private val listFlagsInFilePanel = FlagsInFileListPanel(project)
    private val manageFlagsInFilePanel = ManageFlagsInFilePanel(project, goalStore)

    init {

        val manageFlagsInFileBorder: Border = BorderFactory.createTitledBorder("Manage Flags In File")
        val listFlagsInFileBorder: Border =
            BorderFactory.createTitledBorder(listFlagsInFileTitle + " (" + flagsInFileStore.getFlags().count() + " Flags)")

        manageFlagsInFilePanel.border = manageFlagsInFileBorder
        listFlagsInFilePanel.border = listFlagsInFileBorder

        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = manageFlagsInFilePanel
            secondComponent = listFlagsInFilePanel
        }
        add(splitter, BorderLayout.CENTER)
    }

    fun updateListFlagsInFileBorder() {
        this.listFlagsInFilePanel.border =
            BorderFactory.createTitledBorder(listFlagsInFileTitle + " (" + flagsInFileStore.getFlags().count() + " Flags)")
    }

    fun getFlagsInFileListPanel(): FlagsInFileListPanel {
        return listFlagsInFilePanel
    }

    fun getManageFlagsInFilePanel(): ManageFlagsInFilePanel {
        return manageFlagsInFilePanel
    }
}
