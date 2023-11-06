package com.github.flagshipio.jetbrain.toolWindow.flagsInFile

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.dataClass.Goal
import com.github.flagshipio.jetbrain.store.GoalStore
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.*

class ManageFlagsInFilePanel(
    private var project: Project,
    private var goalStore: GoalStore,
) :
    SimpleToolWindowPanel(false, false), Disposable {

    private fun mainFrame(): JPanel {
        val mainPanel = JPanel();

        val addConfigLabel = JLabel("Add Goal");
        addConfigLabel.border = JBUI.Borders.empty(0, 10, 0, 20)
        mainPanel.add(addConfigLabel);

        val fromCredBtn = JButton("Enter Inputs");
        fromCredBtn.addActionListener {
            updateContent(goalFrame(null))
        }

        mainPanel.setLayout(
            BoxLayout(mainPanel, BoxLayout.X_AXIS)
        );
        fromCredBtn.setAlignmentX(CENTER_ALIGNMENT)
        mainPanel.add(fromCredBtn);

        return mainPanel
    }

    fun goalFrame(editGoal: Goal?): JPanel {
        val typeItem = arrayOf("transaction", "event", "pageview", "screenview")
        val operatorItem = arrayOf("contains", "ignoringParameters", "exact", "regex")

        val goalLabel = JLabel("Add Goal")
        val labelTextField = JTextField(20)
        val typeLabel = JLabel("Type:")
        val typeComboBox = ComboBox(typeItem);
        val operatorLabel = JLabel("Operator:")
        val operatorComboBox = ComboBox(operatorItem);
        val valueLabel = JLabel("Value:")
        val valueTextField = JTextField(20)

        operatorLabel.isVisible = false
        operatorComboBox.isVisible = false
        valueLabel.isVisible = false
        valueTextField.isVisible = false

        if (editGoal != null) {
            goalLabel.text = "Edit Goal"
            labelTextField.text = editGoal.label
            typeComboBox.selectedItem = editGoal.type
            typeComboBox.isEnabled = false
            operatorComboBox.selectedItem = editGoal.operator
            valueTextField.text = editGoal.value

            if ((editGoal.type == "transaction") || (editGoal.type == "event")) {
                operatorLabel.isVisible = false
                operatorComboBox.isVisible = false
                valueLabel.isVisible = false
                valueTextField.isVisible = false
            } else {
                operatorLabel.isVisible = true
                operatorComboBox.isVisible = true
                valueLabel.isVisible = true
                valueTextField.isVisible = true
            }
        }

        val fromCredPanel = JPanel();
        fromCredPanel.setLayout(BorderLayout(0, 0));

        val fromCredSubPanel = JPanel()
        fromCredPanel.add(fromCredSubPanel, BorderLayout.SOUTH)


        goalLabel.setBorder(JBUI.Borders.empty(10, 10, 0, 0))
        fromCredPanel.add(goalLabel, BorderLayout.NORTH)

        val credFormPanel = JPanel()
        credFormPanel.setLayout(BoxLayout(credFormPanel, BoxLayout.Y_AXIS))

        credFormPanel.setBorder(JBUI.Borders.empty(0, 50))

        credFormPanel.add(JLabel("Key:"))
        credFormPanel.add(labelTextField)
        credFormPanel.add(typeLabel)
        credFormPanel.add(typeComboBox)
        credFormPanel.add(operatorLabel)
        credFormPanel.add(operatorComboBox)
        credFormPanel.add(valueLabel)
        credFormPanel.add(valueTextField)
        typeComboBox.addActionListener {

            if ((typeComboBox.selectedItem == "transaction") || (typeComboBox.selectedItem == "event")) {
                operatorLabel.isVisible = false
                operatorComboBox.isVisible = false
                valueLabel.isVisible = false
                valueTextField.isVisible = false
            } else {
                operatorLabel.isVisible = true
                operatorComboBox.isVisible = true
                valueLabel.isVisible = true
                valueTextField.isVisible = true
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
            val goal = Goal(
                null,
                labelTextField.text,
                typeComboBox.item.toString(),
                if (((typeComboBox.item == "transaction") || (typeComboBox.item == "event"))) null else operatorComboBox.item.toString(),
                if (((typeComboBox.item == "transaction") || (typeComboBox.item == "event"))) null else valueTextField.text,
            )
            if (editGoal != null) {
                goalStore.editGoal(editGoal, goal)
            } else {
                goalStore.saveGoal(goal)
            }

            ActionHelpers.getListGoalPanel(project).updateNodeInfo()
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