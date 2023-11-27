package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.toolWindow.RootNode
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
        children.add(RootNode("Id: ${viewModel.flagId}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("$KEY_PREFIX ${viewModel.flagLabel}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Type: ${viewModel.flagType}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Description: ${viewModel.flagDescription}", Debugger.Db_muted_breakpoint))
        if (viewModel.flagType != "boolean") {
            children.add(RootNode("Default value: ${viewModel.defaultValue}", Debugger.Db_muted_breakpoint))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.flagLabel
    }
}
