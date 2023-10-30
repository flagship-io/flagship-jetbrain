package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class VariationGroupNodeParent(private var viewModel: VariationGroupNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val name_: String? get() = viewModel.variationGroup.name

    val variationGroup get() = viewModel.variationGroup

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
        children.add(RootNode("Id: ${viewModel.vgID}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("$NAME_PREFIX ${viewModel.vgName}", Debugger.Db_muted_breakpoint))
        variationGroup.variations?.forEach{
            val variationViewModel = VariationNodeViewModel(it)
            children.add(VariationNodeParent(variationViewModel))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Variation Group: "+viewModel.vgName
    }
}
