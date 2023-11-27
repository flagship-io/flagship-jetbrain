package com.github.flagshipio.jetbrain.toolWindow.flagsInFile

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

const val KEY_PREFIX = "Key:"

class FlagInFileNodeParent(private var viewModel: FlagInFileNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val flagAnalyzed get() = viewModel.flagAnalyzed
    val key = viewModel.flagKey

    override fun getChildren(): Array<SimpleNode> {
        if (children.isNotEmpty()) {
            children = ArrayList()
        }
        addChildren()
        return children.toTypedArray()
    }

    private fun addChildren() {
        children.add(RootNode("$KEY_PREFIX ${viewModel.flagKey}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Type:  ${viewModel.flagType}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Default Value: ${viewModel.flagDefaultValue}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Line number: ${viewModel.flagLineNumber}", Debugger.Db_muted_breakpoint))
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = viewModel.flagKey
        data.tooltip = "Right-Click to display the actions"
    }
}
