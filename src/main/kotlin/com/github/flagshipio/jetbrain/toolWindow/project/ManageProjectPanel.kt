package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.ProjectStore
import com.github.flagshipio.jetbrain.toolWindow.configuration.openFileSystem
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import javax.swing.border.LineBorder

class ManageProjectPanel(
    private var project: Project,
    private var projectStore: ProjectStore,
) :
    SimpleToolWindowPanel(false, false), Disposable {

    private fun mainFrame(): JPanel {
        val mainPanel = JPanel();

        val addProjectLabel = JLabel("Add Project");
        addProjectLabel.border = JBUI.Borders.empty(0, 10, 0, 20)
        mainPanel.add(addProjectLabel);

        val fromCredBtn = JButton("Enter Inputs");
        fromCredBtn.addActionListener {
            updateContent(projectFrame(null))
        }

        val fromResourceLoaderBtn = JButton("From resource loader file");
        fromResourceLoaderBtn.addActionListener {
            updateContent(fromFileFrame())
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
            val projectClass = com.github.flagshipio.jetbrain.dataClass.Project(
                null,
                nameTextField.text,
                null,
            )
            if (editProject != null) {
                projectStore.editProject(editProject, projectClass)
            } else {
                projectStore.saveProject(projectClass)
            }

            ActionHelpers.getProjectPanel(project).updateListProjectBorder()
            ActionHelpers.getListProjectPanel(project).updateNodeInfo()
            updateContent(mainFrame())
        }
        fromCredSubPanel.add(saveBtn)

        return fromCredPanel
    }

    private fun fromFileFrame(): JPanel {

        val fromFilePanel = JPanel();
        fromFilePanel.setLayout(BorderLayout(0, 0));

        val cancelSavePanel = JPanel()
        fromFilePanel.add(cancelSavePanel, BorderLayout.SOUTH)

        val fromFileCancelBtn = JButton("Cancel")
        fromFileCancelBtn.addActionListener {
            updateContent(mainFrame())
        }
        cancelSavePanel.add(fromFileCancelBtn)

        val fromFileSaveBtn = JButton("Save")

        cancelSavePanel.add(fromFileSaveBtn)

        val addConfigLabel = JLabel("Add Flagship Resource");
        addConfigLabel.border = JBUI.Borders.empty(10, 10, 0, 0)
        fromFilePanel.add(addConfigLabel, BorderLayout.NORTH);

        val browserFile = JPanel();
        fromFilePanel.add(browserFile, BorderLayout.CENTER);

        val pathToFileLabel = JLabel("/path/to/file.yaml");
        pathToFileLabel.border = LineBorder(JBColor.BLACK)
        pathToFileLabel.preferredSize = Dimension(900, 30)

        browserFile.setLayout(
            BorderLayout(0, 0)
        );
        pathToFileLabel.setAlignmentX(LEFT_ALIGNMENT);
        browserFile.add(pathToFileLabel, BorderLayout.CENTER)

        var fileChosenPath = ""

        val browserBtn = JButton("Browser");
        browserBtn.setAlignmentX(RIGHT_ALIGNMENT);
        browserFile.border = JBUI.Borders.empty(10, 40, 0, 0)
        browserFile.add(browserBtn, BorderLayout.EAST);

        browserBtn.addActionListener {
            val openedFilePath = openFileSystem(project)
            if (openedFilePath != null) {
                pathToFileLabel.text = openedFilePath
                fileChosenPath = openedFilePath
            }
        }

        fromFileSaveBtn.addActionListener {
            if (fileChosenPath != "") {
                val cliResponse = projectStore.loadResource(fileChosenPath)
                ActionHelpers.getListConfigurationPanel(project).updateNodeInfo()
                updateContent(mainFrame())
                Messages.showInfoMessage(cliResponse, "Information")
            }
        }

        return fromFilePanel
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