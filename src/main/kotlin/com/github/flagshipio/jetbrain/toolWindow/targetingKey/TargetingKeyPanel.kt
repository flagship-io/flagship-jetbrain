package com.github.flagshipio.jetbrain.toolWindow.targetingKey

import com.github.flagshipio.jetbrain.store.TargetingKeyStore
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border

class TargetingKeyPanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "TargetingKeySplitter", .05f)
    private val targetingKeyStore = TargetingKeyStore(project)
    private val listTargetingKeyTitle = "List Targeting Key"

    private val listTargetingKeyPanel = TargetingKeyListPanel(project)
    private val manageTargetingKeyPanel = ManageTargetingKeyPanel(project, targetingKeyStore)

    init {

        val manageTargetingKeyBorder: Border = BorderFactory.createTitledBorder("Manage Targeting Key")
        val listTargetingKeyBorder: Border = BorderFactory.createTitledBorder(listTargetingKeyTitle + " ("+ targetingKeyStore.getTargetingKeys().count() +" Targeting Keys)")

        manageTargetingKeyPanel.border = manageTargetingKeyBorder
        listTargetingKeyPanel.border = listTargetingKeyBorder

        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = manageTargetingKeyPanel
            secondComponent = listTargetingKeyPanel
        }
        add(splitter, BorderLayout.CENTER)
    }

    fun updateListTargetingKeyBorder() {
        this.listTargetingKeyPanel.border = BorderFactory.createTitledBorder(listTargetingKeyTitle + " ("+ targetingKeyStore.getTargetingKeys().count() +" Targeting Keys)")
    }

    fun getTargetingKeyListPanel(): TargetingKeyListPanel {
        return listTargetingKeyPanel
    }

    fun getManageTargetingKeyPanel(): ManageTargetingKeyPanel {
        return manageTargetingKeyPanel
    }
}
