package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.github.flagshipio.jetbrain.toolWindow.project.NAME_PREFIX
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation.modification.ModificationListNodeParent
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation.modification.ModificationNodeViewModel
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class VariationListNodeParent(private var viewModel: VariationNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val name_: String get() = viewModel.variation.name

    val variation get() = viewModel.variation

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
        children.add(RootNode("Id: ${viewModel.variationID}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("$NAME_PREFIX ${viewModel.variationName}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Reference: ${viewModel.variationReference}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("allocation: ${viewModel.variationAllocation}", Debugger.Db_muted_breakpoint))
        val modificationNodeViewModel = ModificationNodeViewModel(viewModel.variationModification)
        children.add(ModificationListNodeParent(modificationNodeViewModel))
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.variationName
    }
}
