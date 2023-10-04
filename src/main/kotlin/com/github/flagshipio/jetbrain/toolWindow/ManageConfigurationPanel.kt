package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.openapi.Disposable
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import com.jgoodies.forms.layout.FormLayout
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.border.LineBorder


class ManageConfigurationPanel :
    SimpleToolWindowPanel(false, false), Disposable {

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
        fromCredBtn.setAlignmentX(Component.CENTER_ALIGNMENT)
        mainPanel.add(fromCredBtn);

        val fromFileBtn = JButton("From file");
        fromFileBtn.addActionListener { e: ActionEvent? ->
            updateContent(fromFileFrame())
        }
        fromFileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(fromFileBtn);

        return mainPanel
    }

    private fun fromCredFrame(): JPanel {
        val fromCredPanel = JPanel();
        fromCredPanel.setLayout(BorderLayout(0, 0));

        val fromCredSubPanel = JPanel()
        fromCredPanel.add(fromCredSubPanel, BorderLayout.SOUTH)


        val cancelBtn = JButton("Cancel")
        cancelBtn.addActionListener { e: ActionEvent? ->
            updateContent(mainFrame())
        }
        fromCredSubPanel.add(cancelBtn)

        val saveBtn = JButton("Save")
        fromCredSubPanel.add(saveBtn)

        val addConfigLabel = JLabel("Add configuration")
        addConfigLabel.setBorder(JBUI.Borders.empty(10, 10, 0, 0))
        fromCredPanel.add(addConfigLabel, BorderLayout.NORTH)

        val credFormPanel = JPanel()
        credFormPanel.setBorder(JBUI.Borders.emptyTop(10))
        fromCredPanel.add(credFormPanel, BorderLayout.CENTER)
        credFormPanel.setLayout(
            FormLayout(
                "7dlu center:120px center:260px",
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

        val lblNewLabel_1 = JLabel("Account Environment ID")
        lblNewLabel_1.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(lblNewLabel_1, "2, 9, right, default")

        val textField = JTextField()
        textField.setBorder(JBUI.Borders.emptyRight(8))
        credFormPanel.add(textField, "3, 9, fill, default")
        textField.setColumns(10)

        return fromCredPanel
    }

    private fun fromFileFrame(): JPanel {

        val fromFilePanel = JPanel();
        fromFilePanel.setLayout(BorderLayout(0, 0));

        val cancelSavePanel = JPanel()
        fromFilePanel.add(cancelSavePanel, BorderLayout.SOUTH)

        val fromFilecancelBtn = JButton("Cancel")
        fromFilecancelBtn.addActionListener { e: ActionEvent? ->
            updateContent(mainFrame())
        }
        cancelSavePanel.add(fromFilecancelBtn)

        val fromFilesaveBtn = JButton("Save")
        cancelSavePanel.add(fromFilesaveBtn)

        val addConfigLabel = JLabel("Add configuration");
        addConfigLabel.border = JBUI.Borders.empty(10, 10, 0, 0)
        fromFilePanel.add(addConfigLabel, BorderLayout.NORTH);

        val browserFile = JPanel();
        fromFilePanel.add(browserFile, BorderLayout.CENTER);


        val pathToFileLabel = JLabel("/path/to/file.yaml");
        pathToFileLabel.border = LineBorder(JBColor.BLACK)
        pathToFileLabel.preferredSize = Dimension(600, 30)

        browserFile.setLayout(
            FormLayout(
                "center:210px center:100px",
                "center:120px"
            )
        );
        pathToFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        browserFile.add(pathToFileLabel, "1, 1, center, center");

        val browserBtn = JButton("Browser");
        browserBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browserFile.border = JBUI.Borders.empty(10, 40, 0, 0)
        browserFile.add(browserBtn, "2, 1, center, center");

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

