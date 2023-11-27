package com.github.flagshipio.jetbrain.toolWindow.goal

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

const val LABEL_PREFIX = "Label:"

class GoalNodeParent(private var viewModel: GoalNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val label: String? get() = viewModel.goal.label

    val goal get() = viewModel.goal

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
        children.add(RootNode("Id: ${viewModel.goalId}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("$LABEL_PREFIX ${viewModel.goalLabel}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Type: ${viewModel.goalType}", Debugger.Db_muted_breakpoint))
        if ((viewModel.goalType == "pageview") || (viewModel.goalType == "screenview")) {
            children.add(RootNode("Operator: ${viewModel.goalOperator}", Debugger.Db_muted_breakpoint))
            children.add(RootNode("Value: ${viewModel.goalValue}", Debugger.Db_muted_breakpoint))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.goalLabel
    }
}
