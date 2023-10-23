package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

const val NAME_PREFIX = "Name:"

class ConfigurationNodeParent(private var viewModel: ConfigurationNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    private val cliCommand = CliCommand()
    val configuration get() = viewModel.configuration

    override fun getChildren(): Array<SimpleNode> {
        if (children.isNotEmpty()) {
            children = ArrayList()
        }
        addChildren()
        return children.toTypedArray()
    }

    private fun addChildren() {
        children.add(RootNode("$NAME_PREFIX ${viewModel.configName}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Client ID:  ${viewModel.configClientID}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Client Secret: ${viewModel.configClientSecret}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Account ID: ${viewModel.configAccountID}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Account Environment ID: ${viewModel.configAccountEnvID}", Debugger.Db_muted_breakpoint))
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = viewModel.configName
        data.tooltip = "Right-Click to display the actions"
        val currentConfig = cliCommand.currentConfigurationCli()
        if (currentConfig != null) {
            if (currentConfig.name == data.presentableText) {
                data.setIcon(AllIcons.Actions.Commit)
            }
        }

    }
}
