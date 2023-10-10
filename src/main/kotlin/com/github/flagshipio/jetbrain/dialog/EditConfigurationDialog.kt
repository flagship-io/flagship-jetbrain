package com.github.flagshipio.jetbrain.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.*

class EditConfigurationDialog(project: Project?) : DialogWrapper(project) {
    private var nameText: JTextField? = null
    private var clientIDText: JTextField? = null
    private var clientSecretText: JTextField? = null
    private var accountIDText: JTextField? = null
    private var accountEnvironmentIDText: JTextField? = null

    init {
        init()
        title = "Edit Configuration"
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel()
        panel.setLayout(BoxLayout(panel, BoxLayout.Y_AXIS))
        nameText = JTextField(20)
        clientIDText = JTextField(20)
        clientSecretText = JTextField(20)
        accountIDText = JTextField(20)
        accountEnvironmentIDText = JTextField(20)

        panel.add(JLabel("Name:"))
        panel.add(nameText)
        panel.add(JLabel("Client ID:"))
        panel.add(clientIDText)
        panel.add(JLabel("Client Secret:"))
        panel.add(clientSecretText)
        panel.add(JLabel("Account ID:"))
        panel.add(accountIDText)
        panel.add(JLabel("Account Environment ID:"))
        panel.add(accountEnvironmentIDText)

        return panel
    }

    val name: String
        get() = nameText!!.getText()
    val clientID: String
        get() = clientIDText!!.getText()

    val clientSecret: String
        get() = clientSecretText!!.getText()

    val accountID: String
        get() = accountIDText!!.getText()

    val accountEnvironmentID: String
        get() = accountEnvironmentIDText!!.getText()

    fun setNameText(name: String){
        nameText!!.text = name
    }
    fun setClientIDText(clientID: String){
        clientIDText!!.text = clientID
    }
    fun setClientSecretText(clientSecret: String){
        clientSecretText!!.text = clientSecret
    }
    fun setAccountIDText(accountID: String){
        accountIDText!!.text = accountID
    }
    fun setAccountEnvironmentIDText(accountEnvironmentID: String){
        accountEnvironmentIDText!!.text = accountEnvironmentID
    }
}

