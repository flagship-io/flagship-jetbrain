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
import org.apache.commons.io.IOUtils
import java.awt.BorderLayout
import java.awt.Dimension
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
        fromCredBtn.addActionListener {
            updateContent(fromCredFrame(null))
        }

        mainPanel.setLayout(
            BoxLayout(mainPanel, BoxLayout.X_AXIS)
        );
        fromCredBtn.setAlignmentX(CENTER_ALIGNMENT)
        mainPanel.add(fromCredBtn);

        val fromFileBtn = JButton("From file");
        fromFileBtn.addActionListener {
            updateContent(fromFileFrame())
        }
        fromFileBtn.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(fromFileBtn);

        return mainPanel
    }

    fun fromCredFrame(editConfiguration: Configuration?): JPanel {
        val configLabel = JLabel("Add configuration")
        val nameTextField = JTextField(20)
        val clientIdTextField = JTextField(20)
        val clientSecretTextField = JTextField(20)
        val accountIdTextField = JTextField(20)
        val accountEnvIdTextField = JTextField(20)

        if (editConfiguration != null) {
            configLabel.text = "Edit configuration"
            nameTextField.text = editConfiguration.name
            clientIdTextField.text = editConfiguration.clientID
            clientSecretTextField.text = editConfiguration.clientSecret
            accountIdTextField.text = editConfiguration.accountID
            accountEnvIdTextField.text = editConfiguration.accountEnvironmentID
        }

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
            updateContent(mainFrame())
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
            if (editConfiguration != null) {
                configurationStoreLocal.editConfiguration(editConfiguration, configuration)
                Messages.showMessageDialog("Configuration edited", "Status", Messages.getInformationIcon())

            } else {
                configurationStoreLocal.saveConfiguration(configuration)
                Messages.showMessageDialog("Configuration saved", "Status", Messages.getInformationIcon())
            }
            listConfigPanelLocal.updateNodeInfo()
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

        val addConfigLabel = JLabel("Add configuration");
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

        var fileChosenPath: String = ""

        val browserBtn = JButton("Browser");
        browserBtn.setAlignmentX(RIGHT_ALIGNMENT);
        browserFile.border = JBUI.Borders.empty(10, 40, 0, 0)
        browserFile.add(browserBtn, BorderLayout.EAST);

        browserBtn.addActionListener {
            val openedFilePath = openFileSystem(projectLocal)
            if (openedFilePath != null) {
                pathToFileLabel.text = openedFilePath
                fileChosenPath = openedFilePath
            }
        }

        fromFileSaveBtn.addActionListener {
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

    fun updateContent(content: JPanel) {
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
