package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.messaging.DefaultMessageBusService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border


class ConfigurationToolWindow(project: Project) : JPanel() {
    private val messageBus = project.service<DefaultMessageBusService>()
    private val splitter = OnePixelSplitter(true, "FSSplitterProportion", .25f)

    private val manageConfigurationPanel = ManageConfigurationPanel()
    private val listConfigurationPanel = ConfigurationPanel(project, messageBus)

    init {
        val manageConfigurationBorder: Border = BorderFactory.createTitledBorder("Manage configuration")
        val listConfigurationBorder: Border = BorderFactory.createTitledBorder("List configuration")

        manageConfigurationPanel.border = manageConfigurationBorder
        listConfigurationPanel.border = listConfigurationBorder

        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = manageConfigurationPanel
            secondComponent = listConfigurationPanel
        }
        add(splitter, BorderLayout.CENTER)
    }
}
