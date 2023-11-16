package com.github.flagshipio.jetbrain.toolWindow.quickLink

import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border

class LinkPanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "LinkSplitter", .05f)
    private val quickLinkTitle = "Quick Link"

    private val linkListPanel = LinkListPanel(project)

    init {

        val listLinkBorder: Border = BorderFactory.createTitledBorder(quickLinkTitle)

        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = linkListPanel
        }

        linkListPanel.border = listLinkBorder
        add(splitter, BorderLayout.CENTER)
    }

    fun getLinkListPanel(): LinkListPanel {
        return linkListPanel
    }
}