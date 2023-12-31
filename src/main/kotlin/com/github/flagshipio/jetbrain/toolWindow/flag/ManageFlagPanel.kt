package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.store.FlagStore
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*


class ManageFlagPanel(
    private var project: Project,
    private var flagStore: FlagStore,
) :
    SimpleToolWindowPanel(false, false), Disposable {

    private fun mainFrame(): JPanel {
        val mainPanel = JPanel();

        val addConfigLabel = JLabel("Add Feature Flag");
        addConfigLabel.border = JBUI.Borders.empty(0, 10, 0, 20)
        mainPanel.add(addConfigLabel);

        val fromCredBtn = JButton("Enter Inputs");
        fromCredBtn.addActionListener {
            updateContent(featureFlagFrame(null))
        }

        mainPanel.setLayout(
            BoxLayout(mainPanel, BoxLayout.X_AXIS)
        );
        fromCredBtn.setAlignmentX(CENTER_ALIGNMENT)
        mainPanel.add(fromCredBtn);

        return mainPanel
    }

    fun featureFlagFrame(editFlag: Flag?): JPanel {
        val typeItem = arrayOf("string", "boolean", "number", "array", "object")

        val flagLabel = JLabel("Add Feature Flag")
        val keyTextField = JTextField(20)
        val typeLabel = JLabel("Type:")
        val typeComboBox = ComboBox(typeItem);
        val descriptionTextField = JTextField(20)
        val defaultValueLabel = JLabel("Default Value:")
        val defaultValueTextField = JTextField(20)

        if (editFlag != null) {
            flagLabel.text = "Edit Feature Flag"
            keyTextField.text = editFlag.name
            typeComboBox.selectedItem = editFlag.type
            typeComboBox.isEnabled = false
            descriptionTextField.text = editFlag.description
            defaultValueTextField.text = editFlag.defaultValue

            if (editFlag.type == "boolean") {
                defaultValueLabel.isVisible = false
                defaultValueTextField.isVisible = false
            } else {
                defaultValueLabel.isVisible = true
                defaultValueTextField.isVisible = true
            }
        }

        val fromCredPanel = JPanel();
        fromCredPanel.setLayout(BorderLayout(0, 0));

        val fromCredSubPanel = JPanel()
        fromCredPanel.add(fromCredSubPanel, BorderLayout.SOUTH)


        flagLabel.setBorder(JBUI.Borders.empty(10, 10, 0, 0))
        fromCredPanel.add(flagLabel, BorderLayout.NORTH)

        val credFormPanel = JPanel()
        credFormPanel.setLayout(BoxLayout(credFormPanel, BoxLayout.Y_AXIS))

        credFormPanel.setBorder(JBUI.Borders.empty(0, 50))

        val innerKeyLabel = JPanel(FlowLayout(FlowLayout.LEFT))
        innerKeyLabel.add(JLabel("Key:"))
        credFormPanel.add(innerKeyLabel)

        credFormPanel.add(keyTextField)

        val innerTypeLabel = JPanel(FlowLayout(FlowLayout.LEFT))
        innerTypeLabel.add(typeLabel)
        credFormPanel.add(innerTypeLabel)

        credFormPanel.add(typeComboBox)

        val innerDescriptionLabel = JPanel(FlowLayout(FlowLayout.LEFT))
        innerDescriptionLabel.add(JLabel("Description:"))
        credFormPanel.add(innerDescriptionLabel)

        credFormPanel.add(descriptionTextField)

        val innerDefaultValueLabel = JPanel(FlowLayout(FlowLayout.LEFT))
        innerDefaultValueLabel.add(defaultValueLabel)
        credFormPanel.add(innerDefaultValueLabel)

        credFormPanel.add(defaultValueTextField)

        typeComboBox.addActionListener {
            if (typeComboBox.selectedItem == "boolean") {
                defaultValueLabel.isVisible = false
                defaultValueTextField.isVisible = false
            } else {
                defaultValueLabel.isVisible = true
                defaultValueTextField.isVisible = true
            }
        }
        fromCredPanel.add(credFormPanel, BorderLayout.CENTER)

        val cancelBtn = JButton("Cancel")
        cancelBtn.addActionListener {
            updateContent(mainFrame())
        }
        fromCredSubPanel.add(cancelBtn)

        val saveBtn = JButton("Save")
        saveBtn.addActionListener {
            val featureFlag = Flag(
                null,
                keyTextField.text,
                typeComboBox.item.toString(),
                descriptionTextField.text,
                defaultValueTextField.text
            )
            if (editFlag != null) {
                flagStore.editFlag(editFlag, featureFlag)
            } else {
                flagStore.saveFlag(featureFlag)
            }

            ActionHelpers.getListFlagPanel(project).updateNodeInfo()
            updateContent(mainFrame())
        }
        fromCredSubPanel.add(saveBtn)

        return fromCredPanel
    }

    override fun dispose() {
    }

    init {
        this.setContent(mainFrame())
    }

    fun updateContent(content: JPanel) {
        this.setContent(content)
    }

}