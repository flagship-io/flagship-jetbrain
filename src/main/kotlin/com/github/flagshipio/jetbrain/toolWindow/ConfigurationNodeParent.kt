package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.icons.AllIcons.Debugger
import com.intellij.icons.AllIcons.Icons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode
import java.util.*

const val KEY_PREFIX_1 = "name:"

class ConfigurationNodeParent(private var viewModel: ConfigurationNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val configuration get() = viewModel.configuration

    override fun getChildren(): Array<SimpleNode> {
        if (children.isEmpty()) {
            buildChildren()
        } else {
            children = ArrayList()
            buildChildren()
        }
        return children.toTypedArray()
    }

    fun updateViewModel(viewModel: ConfigurationNodeViewModel) {
        this.viewModel = viewModel
    }

    private fun buildChildren() {
        children.add(NodeBase("Name: ${viewModel.configName}", Debugger.Db_muted_breakpoint))
        children.add(NodeBase("Client ID:  ${viewModel.configClientID}", Debugger.Db_muted_breakpoint))
        children.add(NodeBase("Client Secret: ${viewModel.configClientSecret}", Debugger.Db_muted_breakpoint))
        children.add(NodeBase("Account ID: ${viewModel.configAccountID}", Debugger.Db_muted_breakpoint))
        children.add(NodeBase("Account Environment ID: ${viewModel.configAccountEnvID}", Debugger.Db_muted_breakpoint))
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.configName
    }
}
