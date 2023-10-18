package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.messaging.DefaultMessageBusService
import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border


class ConfigurationPanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "LDSplitterProportion", .25f)
    private val configurationStore = ConfigurationStore(project)

    private val listConfigurationPanel = ConfigurationListPanel(project)
    private val manageConfigurationPanel = ManageConfigurationPanel(project, configurationStore, listConfigurationPanel)

    init {
        val listConfigTitle = "List Configuration"
        val manageConfigurationBorder: Border = BorderFactory.createTitledBorder("Manage Configuration")
        val listConfigurationBorder: Border = BorderFactory.createTitledBorder(listConfigTitle)

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

    fun getListConfigurationPanel(): ConfigurationListPanel {
        return listConfigurationPanel
    }

    fun getManageConfigurationPanel(): ManageConfigurationPanel {
        return manageConfigurationPanel
    }
}
