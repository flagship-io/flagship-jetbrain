package com.github.flagshipio.jetbrain.toolWindow.targetingKey

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

const val NAME_PREFIX = "Name:"

class TargetingKeyNodeParent(private var viewModel: TargetingKeyNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val name_: String? get() = viewModel.targetingKey.name

    val targetingKey get() = viewModel.targetingKey

    override fun getChildren(): Array<SimpleNode> {
        if (children.isEmpty()) {
            buildChildren()
        } else {
            children = ArrayList()
            buildChildren()
        }
        return children.toTypedArray()
    }

    private fun buildChildren() {
        children.add(RootNode("Id: ${viewModel.targetingKeyId}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("$NAME_PREFIX ${viewModel.targetingKeyName}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Type: ${viewModel.targetingKeyType}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Description: ${viewModel.targetingKeyDescription}", Debugger.Db_muted_breakpoint))

    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.targetingKeyName
    }
}
