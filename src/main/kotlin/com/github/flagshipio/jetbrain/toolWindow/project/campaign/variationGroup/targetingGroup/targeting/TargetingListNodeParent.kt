package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.targetingGroup.targeting

import com.github.flagshipio.jetbrain.dataClass.Targeting
import com.github.flagshipio.jetbrain.dataClass.TargetingGroup
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class TargetingListNodeParent(private var viewModel: TargetingNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()

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
        children.add(RootNode("Key: ${viewModel.targetingKey}", AllIcons.Debugger.Db_muted_breakpoint))
        children.add(RootNode("Operator: ${viewModel.targetingOperator}", AllIcons.Debugger.Db_muted_breakpoint))
        children.add(RootNode("Value: ${viewModel.targetingValue}", AllIcons.Debugger.Db_muted_breakpoint))
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.targetingKey
    }
}
