package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation.modification

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.google.gson.internal.LinkedTreeMap
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class ModificationListNodeParent(private var viewModel: ModificationNodeViewModel) : SimpleNode() {
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
        children.add(RootNode("Type: ${viewModel.modificationType}"))
        if (viewModel.modificationValue is LinkedTreeMap <*, *>) {
            val modificationValueLinkedTree = viewModel.modificationValue as LinkedTreeMap<*, *>
            children.add(ModificationValueNodeParent(modificationValueLinkedTree))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = "Modification"
    }
}
