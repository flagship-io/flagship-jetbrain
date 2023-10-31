package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.ProjectStore
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.*

class ManageProjectPanel(
    project: Project,
    projectStore: ProjectStore,
) :
    SimpleToolWindowPanel(false, false), Disposable {
    private val projectStoreLocal: ProjectStore = projectStore
    private val projectLocal: Project = project

    private fun mainFrame(): JPanel {
        val mainPanel = JPanel();

        val addProjectLabel = JLabel("Add Project");
        addProjectLabel.border = JBUI.Borders.empty(0, 10, 0, 20)
        mainPanel.add(addProjectLabel);

        val fromCredBtn = JButton("Enter Inputs");
        fromCredBtn.addActionListener {
            updateContent(projectFrame(null))
        }

        mainPanel.setLayout(
            BoxLayout(mainPanel, BoxLayout.X_AXIS)
        );
        fromCredBtn.setAlignmentX(CENTER_ALIGNMENT)
        mainPanel.add(fromCredBtn);

        return mainPanel
    }

    fun projectFrame(editProject: com.github.flagshipio.jetbrain.dataClass.Project?): JPanel {

        val projectLabel = JLabel("Add Project")
        val nameTextField = JTextField(20)

        if (editProject != null) {
            projectLabel.text = "Edit Project"
            nameTextField.text = editProject.name
        }

        val fromCredPanel = JPanel();
        fromCredPanel.setLayout(BorderLayout(0, 0));

        val fromCredSubPanel = JPanel()
        fromCredPanel.add(fromCredSubPanel, BorderLayout.SOUTH)


        projectLabel.setBorder(JBUI.Borders.empty(10, 10, 0, 0))
        fromCredPanel.add(projectLabel, BorderLayout.NORTH)

        val credFormPanel = JPanel()
        credFormPanel.setLayout(BoxLayout(credFormPanel, BoxLayout.Y_AXIS))

        credFormPanel.setBorder(JBUI.Borders.empty(0, 50))

        credFormPanel.add(JLabel("Name:"))
        credFormPanel.add(nameTextField)

        fromCredPanel.add(credFormPanel, BorderLayout.CENTER)

        val cancelBtn = JButton("Cancel")
        cancelBtn.addActionListener {
            updateContent(mainFrame())
        }
        fromCredSubPanel.add(cancelBtn)

        val saveBtn = JButton("Save")
        saveBtn.addActionListener {
            val project = com.github.flagshipio.jetbrain.dataClass.Project(
                null,
                nameTextField.text,
                null,
            )
            if (editProject != null) {
                projectStoreLocal.editProject(editProject, project)
            } else {
                projectStoreLocal.saveProject(project)
            }

            ActionHelpers.getProjectPanel(projectLocal).updateListProjectBorder()
            ActionHelpers.getListProjectPanel(projectLocal).updateNodeInfo()
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