package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border

class ConfigurationPanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "ConfigurationSplitter", .05f)
    private val configurationStore = ConfigurationStore(project)
    private val listConfigTitle = "List Configuration"

    private val listConfigurationPanel = ConfigurationListPanel(project)
    private val manageConfigurationPanel = ManageConfigurationPanel(project, configurationStore)


    init {
        val manageConfigurationBorder: Border = BorderFactory.createTitledBorder("Manage Configuration")
        val listConfigurationBorder: Border = BorderFactory.createTitledBorder(
            listConfigTitle + " (" + configurationStore.getConfigurations().count() + " Configurations)"
        )

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

    fun updateListConfigurationBorder() {
        this.listConfigurationPanel.border = BorderFactory.createTitledBorder(
            listConfigTitle + " (" + configurationStore.getConfigurations().count() + " Configurations)"
        )
    }

    fun getListConfigurationPanel(): ConfigurationListPanel {
        return listConfigurationPanel
    }

    fun getManageConfigurationPanel(): ManageConfigurationPanel {
        return manageConfigurationPanel
    }
}
