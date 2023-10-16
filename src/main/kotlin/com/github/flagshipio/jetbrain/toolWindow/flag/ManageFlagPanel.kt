package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.github.flagshipio.jetbrain.store.FlagStore
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationListPanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.*

class ManageFlagPanel(
    project: Project,
    flagStore: FlagStore,
    listFlagPanel: FlagListPanel
) :
    SimpleToolWindowPanel(false, false), Disposable {
    private val flagStoreLocal: FlagStore = flagStore
    private val listFlagPanelLocal: FlagListPanel = listFlagPanel
    private val projectLocal: Project = project
    fun featureFlagFrame(): JPanel {
        val configLabel = JLabel("Add Flag")
        val nameTextField = JTextField(20)
        val clientIdTextField = JTextField(20)
        val clientSecretTextField = JTextField(20)
        val accountIdTextField = JTextField(20)
        val accountEnvIdTextField = JTextField(20)

        val fromCredPanel = JPanel();
        fromCredPanel.setLayout(BorderLayout(0, 0));

        val fromCredSubPanel = JPanel()
        fromCredPanel.add(fromCredSubPanel, BorderLayout.SOUTH)


        configLabel.setBorder(JBUI.Borders.empty(10, 10, 0, 0))
        fromCredPanel.add(configLabel, BorderLayout.NORTH)

        val credFormPanel = JPanel()
        credFormPanel.setLayout(BoxLayout(credFormPanel, BoxLayout.Y_AXIS))

        credFormPanel.setBorder(JBUI.Borders.empty(0, 50))

        credFormPanel.add(JLabel("Name:"))
        credFormPanel.add(nameTextField)
        credFormPanel.add(JLabel("Client ID:"))
        credFormPanel.add(clientIdTextField)
        credFormPanel.add(JLabel("Client Secret:"))
        credFormPanel.add(clientSecretTextField)
        credFormPanel.add(JLabel("Account ID:"))
        credFormPanel.add(accountIdTextField)
        credFormPanel.add(JLabel("Account Environment ID:"))
        credFormPanel.add(accountEnvIdTextField)
        fromCredPanel.add(credFormPanel, BorderLayout.CENTER)

        val cancelBtn = JButton("Cancel")
        cancelBtn.addActionListener {
            println("clear all")
        }
        fromCredSubPanel.add(cancelBtn)

        val saveBtn = JButton("Save")
        saveBtn.addActionListener {
            val configuration = Configuration(
                nameTextField.text,
                clientIdTextField.text,
                clientSecretTextField.text,
                accountIdTextField.text,
                accountEnvIdTextField.text
            )
                //configurationStoreLocal.saveConfiguration(configuration)
                Messages.showMessageDialog("Flag saved", "Status", Messages.getInformationIcon())
            //listConfigPanelLocal.updateNodeInfo()
        }
        fromCredSubPanel.add(saveBtn)

        return fromCredPanel
    }

    override fun dispose() {
    }

    init {
        this.setContent(featureFlagFrame())
    }

}