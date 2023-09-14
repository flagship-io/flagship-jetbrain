package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.icons.AllIcons.Debugger
import com.intellij.icons.AllIcons.Icons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode
import java.util.*

const val KEY_PREFIX = "Key:"

class FlagNodeParent(private var viewModel: FlagNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val flag get() = viewModel.flag
    val key: String? get() = viewModel.flag.name

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
        children.add(FlagNodeBase("Id: ${viewModel.flagId}", Debugger.Db_muted_breakpoint))
        children.add(FlagNodeBase("$KEY_PREFIX ${flag.name}", Debugger.Db_muted_breakpoint))
        children.add(FlagNodeBase("Type: ${viewModel.flagType}", Debugger.Db_muted_breakpoint))
        children.add(FlagNodeBase("Description: ${viewModel.flagDescription}", Debugger.Db_muted_breakpoint))
        children.add(FlagNodeBase("Default value: ${viewModel.defaultValue}", Debugger.Db_muted_breakpoint))
        children.add(FlagNodeBase("Source: ${viewModel.flagSource}", Debugger.Db_muted_breakpoint))
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.flagLabel
    }
}
