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

    init {
        val listFlagsInFileBorder: Border =
            BorderFactory.createTitledBorder(listFlagsInFileTitle)

        listFlagsInFilePanel.border = listFlagsInFileBorder

        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = listFlagsInFilePanel
        }
        add(splitter, BorderLayout.CENTER)
    }

    fun getFlagsInFileListPanel(): FlagsInFileListPanel {
        return listFlagsInFilePanel
    }
}
