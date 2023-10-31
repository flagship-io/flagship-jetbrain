package com.github.flagshipio.jetbrain.toolWindow.targetingKey

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.dataClass.TargetingKey
import com.github.flagshipio.jetbrain.store.TargetingKeyStore
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.*

class ManageTargetingKeyPanel(
    private var project: Project,
    private var targetingKeyStore: TargetingKeyStore,
) :
    SimpleToolWindowPanel(false, false), Disposable {

    private fun mainFrame(): JPanel {
        val mainPanel = JPanel();

        val addTargetingKeyLabel = JLabel("Add Targeting Key");
        addTargetingKeyLabel.border = JBUI.Borders.empty(0, 10, 0, 20)
        mainPanel.add(addTargetingKeyLabel);

        val fromCredBtn = JButton("Enter Inputs");
        fromCredBtn.addActionListener {
            updateContent(targetingKeyFrame(null))
        }

        mainPanel.setLayout(
            BoxLayout(mainPanel, BoxLayout.X_AXIS)
        );
        fromCredBtn.setAlignmentX(CENTER_ALIGNMENT)
        mainPanel.add(fromCredBtn);

        return mainPanel
    }

    fun targetingKeyFrame(editTargetingKey: TargetingKey?): JPanel {
        val typeItem = arrayOf("string", "boolean", "number")

        val targetingKeyLabel = JLabel("Add Targeting Key")
        val nameTextField = JTextField(20)
        val typeLabel = JLabel("Type:")
        val typeComboBox = ComboBox(typeItem);
        val descriptionLabel = JLabel("Description:")
        val descriptionTextField = JTextField(20)


        if (editTargetingKey != null) {
            targetingKeyLabel.text = "Edit Targeting Key"
            nameTextField.text = editTargetingKey.name
            typeComboBox.selectedItem = editTargetingKey.type
            typeComboBox.isEnabled = false
            descriptionTextField.text = editTargetingKey.description
        }

        val fromCredPanel = JPanel();
        fromCredPanel.setLayout(BorderLayout(0, 0));

        val fromCredSubPanel = JPanel()
        fromCredPanel.add(fromCredSubPanel, BorderLayout.SOUTH)


        targetingKeyLabel.setBorder(JBUI.Borders.empty(10, 10, 0, 0))
        fromCredPanel.add(targetingKeyLabel, BorderLayout.NORTH)

        val credFormPanel = JPanel()
        credFormPanel.setLayout(BoxLayout(credFormPanel, BoxLayout.Y_AXIS))

        credFormPanel.setBorder(JBUI.Borders.empty(0, 50))

        credFormPanel.add(JLabel("Name:"))
        credFormPanel.add(nameTextField)
        credFormPanel.add(typeLabel)
        credFormPanel.add(typeComboBox)
        credFormPanel.add(descriptionLabel)
        credFormPanel.add(descriptionTextField)

        fromCredPanel.add(credFormPanel, BorderLayout.CENTER)

        val cancelBtn = JButton("Cancel")
        cancelBtn.addActionListener {
            updateContent(mainFrame())
        }
        fromCredSubPanel.add(cancelBtn)

        val saveBtn = JButton("Save")
        saveBtn.addActionListener {
            val targetingKey = TargetingKey(
                null,
                nameTextField.text,
                typeComboBox.item.toString(),
                descriptionTextField.text,
            )
            if (editTargetingKey != null) {
                targetingKeyStore.editTargetingKey(editTargetingKey, targetingKey)
            } else {
                targetingKeyStore.saveTargetingKey(targetingKey)
            }


            ActionHelpers.getTargetingKeyPanel(project).updateListTargetingKeyBorder()
            ActionHelpers.getListTargetingKeyPanel(project).updateNodeInfo()
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