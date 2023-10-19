package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.toolWindow.NodeBase
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

const val KEY_PREFIX = "Key:"

class FlagNodeParent(private var viewModel: FlagNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val key: String? get() = viewModel.flag.name

    val flag get() = viewModel.flag

    override fun getChildren(): Array<SimpleNode> {
        if (children.isEmpty()) {
            buildChildren()
        } else {
            children = ArrayList()
            buildChildren()
        }
        return children.toTypedArray()
    }

    fun updateViewModel(viewModel: FlagNodeViewModel) {
        this.viewModel = viewModel
    }

    private fun buildChildren() {
        children.add(NodeBase("Id: ${viewModel.flagId}", Debugger.Db_muted_breakpoint))
        children.add(NodeBase("$KEY_PREFIX ${viewModel.flagLabel}", Debugger.Db_muted_breakpoint))
        children.add(NodeBase("Type: ${viewModel.flagType}", Debugger.Db_muted_breakpoint))
        children.add(NodeBase("Description: ${viewModel.flagDescription}", Debugger.Db_muted_breakpoint))
        if (viewModel.flagType != "boolean") {
            children.add(NodeBase("Default value: ${viewModel.defaultValue}", Debugger.Db_muted_breakpoint))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.flagLabel
    }
}
