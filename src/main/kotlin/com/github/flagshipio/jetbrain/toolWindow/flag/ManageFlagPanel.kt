package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.store.FlagStore
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
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
        val typeComboBox = ComboBox(typeItem);
        val descriptionTextField = JTextField(20)
        val descriptionLabel = JLabel("Description:")
        val defaultValueTextField = JTextField(20)

        if (editFlag != null) {
            flagLabel.text = "Edit Feature Flag"
            keyTextField.text = editFlag.name
            typeComboBox.item = editFlag.type.toString()
            descriptionTextField.text = editFlag.description
            defaultValueTextField.text = editFlag.defaultValue
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

        credFormPanel.add(JLabel("Key:"))
        credFormPanel.add(keyTextField)
        credFormPanel.add(JLabel("Type:"))
        credFormPanel.add(typeComboBox)
        credFormPanel.add(descriptionLabel)
        credFormPanel.add(descriptionTextField)
        credFormPanel.add(JLabel("Default Value:"))
        credFormPanel.add(defaultValueTextField)
        typeComboBox.addActionListener {
            if (typeComboBox.selectedItem == "boolean"){
                descriptionLabel.isVisible = false
                descriptionTextField.isVisible = false
            } else {
                descriptionLabel.isVisible = true
                descriptionTextField.isVisible = true
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
                null,
                defaultValueTextField.text
            )
            if (editFlag != null){
                //flagStoreLocal
                Messages.showMessageDialog("Feature Flag edited", "Status", Messages.getInformationIcon())
            }else {
                //flagStoreLocal
                flagStoreLocal.saveFlag(featureFlag)
                Messages.showMessageDialog("Feature Flag saved", "Status", Messages.getInformationIcon())
            }
            listFlagPanelLocal.updateNodeInfo()
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