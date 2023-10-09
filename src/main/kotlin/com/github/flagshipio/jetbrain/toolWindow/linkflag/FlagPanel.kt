package com.github.flagshipio.jetbrain.toolWindow.linkflag

import com.github.flagshipio.jetbrain.messaging.DefaultMessageBusService
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
    private val linkPanel = LinkPanel(project)
    private val flagListPanel = FlagListPanel(project, messageBus)

    init {

        val quickLinksTitle: Border = BorderFactory.createTitledBorder("Quick Links")
        val flagsTitle: Border = BorderFactory.createTitledBorder("Feature Flags")

        linkPanel.border = quickLinksTitle
        flagListPanel.border = flagsTitle
        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = linkPanel
            secondComponent = flagListPanel

        }
        add(splitter, BorderLayout.CENTER)
    }

    fun getFlagListPanel(): FlagListPanel {
        return flagListPanel
    }

}
