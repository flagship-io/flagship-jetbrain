package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.intellij.openapi.Disposable
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import com.jgoodies.forms.layout.FormLayout
import org.apache.commons.io.IOUtils
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.swing.*
import javax.swing.border.LineBorder

class ManageConfigurationPanel(
    project: Project,
    configurationStore: ConfigurationStore,
    listConfigPanel: ConfigurationListPanel
) :
    SimpleToolWindowPanel(false, false), Disposable {
    private val configurationStoreLocal: ConfigurationStore = configurationStore
    private val listConfigPanelLocal: ConfigurationListPanel = listConfigPanel
    private val projectLocal: Project = project
    private fun mainFrame(): JPanel {
        val mainPanel = JPanel();

        val addConfigLabel = JLabel("Add configuration");
        addConfigLabel.border = JBUI.Borders.empty(0, 10, 0, 20)
        mainPanel.add(addConfigLabel);

        val fromCredBtn = JButton("From credentials");
        fromCredBtn.addActionListener { e: ActionEvent? ->
            updateContent(fromCredFrame())
        }

        mainPanel.setLayout(
            BoxLayout(mainPanel, BoxLayout.X_AXIS)
        );
        fromCredBtn.setAlignmentX(CENTER_ALIGNMENT)
        mainPanel.add(fromCredBtn);

        val fromFileBtn = JButton("From file");
        fromFileBtn.addActionListener { e: ActionEvent? ->
            updateContent(fromFileFrame())
        }
        fromFileBtn.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(fromFileBtn);

        return mainPanel
    }

    private fun fromCredFrame(): JPanel {
        val fromCredPanel = JPanel();
        fromCredPanel.setLayout(BorderLayout(0, 0));

        val fromCredSubPanel = JPanel()
        fromCredPanel.add(fromCredSubPanel, BorderLayout.SOUTH)

        val addConfigLabel = JLabel("Add configuration")
        addConfigLabel.setBorder(JBUI.Borders.empty(10, 10, 0, 0))
        fromCredPanel.add(addConfigLabel, BorderLayout.NORTH)

        val credFormPanel = JPanel()
        credFormPanel.setBorder(JBUI.Borders.emptyTop(10))
        fromCredPanel.add(credFormPanel, BorderLayout.CENTER)
        credFormPanel.setLayout(
            FormLayout(
                "7dlu center:200px center:260px",
                "25px 15dlu 25px 7dlu 25px 7dlu 25px 7dlu 25px"
            )
        )

        val nameLabel = JLabel("Name")
        nameLabel.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(nameLabel, "2, 1, right, default")

        val nameTextField = JTextField()
        nameTextField.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(nameTextField, "3, 1, fill, default")
        nameTextField.setColumns(10)

        val clientIdLabel = JLabel("Client ID")
        clientIdLabel.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(clientIdLabel, "2, 3, right, default")

        val clientIdTextField = JTextField()
        clientIdTextField.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(clientIdTextField, "3, 3, fill, default")
        clientIdTextField.setColumns(10)

        val clientSecretLabel = JLabel("Client Secret")
        clientSecretLabel.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(clientSecretLabel, "2, 5, right, default")

        val clientSecretTextField = JTextField()
        clientSecretTextField.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(clientSecretTextField, "3, 5, fill, default")
        clientSecretTextField.setColumns(10)

        val accountIdLabel = JLabel("Account ID")
        accountIdLabel.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(accountIdLabel, "2, 7, right, default")

        val accountIdTextField = JTextField()
        accountIdTextField.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(accountIdTextField, "3, 7, fill, default")
        accountIdTextField.setColumns(10)

        val accountEnvIdLabel = JLabel("Account Environment ID")
        accountEnvIdLabel.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(accountEnvIdLabel, "2, 9, right, default")

        val accountEnvIdTextField = JTextField()
        accountEnvIdTextField.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(accountEnvIdTextField, "3, 9, fill, default")
        accountEnvIdTextField.setColumns(10)

        val cancelBtn = JButton("Cancel")
        cancelBtn.addActionListener { e: ActionEvent? ->
            updateContent(mainFrame())
        }
        fromCredSubPanel.add(cancelBtn)

        val saveBtn = JButton("Save")
        fromCredSubPanel.add(saveBtn)
        saveBtn.addActionListener { e: ActionEvent? ->
            val configuration = Configuration(
                nameTextField.text,
                clientIdTextField.text,
                clientSecretTextField.text,
                accountIdTextField.text,
                accountEnvIdTextField.text
            )
            configurationStoreLocal.saveConfiguration(configuration)
            listConfigPanelLocal.updateNodeInfo()
        }

        return fromCredPanel
    }

    private fun fromFileFrame(): JPanel {

        val fromFilePanel = JPanel();
        fromFilePanel.setLayout(BorderLayout(0, 0));

        val cancelSavePanel = JPanel()
        fromFilePanel.add(cancelSavePanel, BorderLayout.SOUTH)

        val fromFileCancelBtn = JButton("Cancel")
        fromFileCancelBtn.addActionListener { e: ActionEvent? ->
            updateContent(mainFrame())
        }
        cancelSavePanel.add(fromFileCancelBtn)

        val fromFileSaveBtn = JButton("Save")

        cancelSavePanel.add(fromFileSaveBtn)

        val addConfigLabel = JLabel("Add configuration");
        addConfigLabel.border = JBUI.Borders.empty(10, 10, 0, 0)
        fromFilePanel.add(addConfigLabel, BorderLayout.NORTH);

        val browserFile = JPanel();
        fromFilePanel.add(browserFile, BorderLayout.CENTER);

        var pathToFileLabel = JLabel("/path/to/file.yaml");
        pathToFileLabel.border = LineBorder(JBColor.BLACK)
        pathToFileLabel.preferredSize = Dimension(900, 30)

        browserFile.setLayout(
            BorderLayout(0, 0)
        );
        pathToFileLabel.setAlignmentX(LEFT_ALIGNMENT);
        browserFile.add(pathToFileLabel, BorderLayout.CENTER)

        var fileChosenPath: String = ""

        val browserBtn = JButton("Browser");
        browserBtn.setAlignmentX(RIGHT_ALIGNMENT);
        browserFile.border = JBUI.Borders.empty(10, 40, 0, 0)
        browserFile.add(browserBtn, BorderLayout.EAST);

        browserBtn.addActionListener { e: ActionEvent? ->
            val openedFilePath = openFileSystem(projectLocal)
            if (openedFilePath != null) {
                pathToFileLabel.text = openedFilePath
                fileChosenPath = openedFilePath
            }
        }

        fromFileSaveBtn.addActionListener { e: ActionEvent? ->
            if (fileChosenPath != "") {
                val cliResponse = configurationStoreLocal.saveConfigurationFromFile(fileChosenPath)
                listConfigPanelLocal.updateNodeInfo()
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

    private fun updateContent(content: JPanel) {
        this.setContent(content)
    }
}

private fun openFileSystem(project: Project): String? {
    val fileChooserDialog = FileChooserFactory.getInstance().createFileChooser(
        createFileChooserDescriptor(),
        project,
        null
    )
    val selectedFiles = fileChooserDialog.choose(project)
    if (selectedFiles.isEmpty()) {
        return null
    }
    val content = readYmlFileContent(selectedFiles[0])
    if (content == null) {
        Messages.showErrorDialog("Failed to read YML file content.", "Error")
    }

    for (selectedFile in selectedFiles) {
        val selectedIoFile = File(selectedFile.path)

        if (selectedIoFile.exists() && !selectedIoFile.isDirectory()) {
            return selectedIoFile.path
        }
    }

    return null
}

private fun createFileChooserDescriptor(): FileChooserDescriptor {
    val descriptor = FileChooserDescriptor(true, false, false, false, false, false)
    descriptor.title = "Open File"
    descriptor.description = "Select a file to open in IntelliJ IDEA"
    descriptor.withFileFilter {
        it.isDirectory || it.name.lowercase().endsWith(".yml") || it.name.lowercase()
            .endsWith(".yaml") || it.name.lowercase().endsWith(".json")
    }
    return descriptor
}

private fun readYmlFileContent(file: VirtualFile): String? {
    try {
        val inputStream = file.inputStream
        val content = IOUtils.toString(inputStream, StandardCharsets.UTF_8)
        inputStream.close()
        return content
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}
