package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup

import com.github.flagshipio.jetbrain.dataClass.VariationGroup
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class VariationGroupNodeParent(private var variationGroups: ArrayList<VariationGroup>?) : SimpleNode() {
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
        if (variationGroups == null || variationGroups!!.size == 0) {
            children.add(RootNode("No Variation Groups"))
            return
        }

        variationGroups?.forEach {
            val variationGroupViewModel = VariationGroupNodeViewModel(it)
            children.add(VariationGroupListNodeParent(variationGroupViewModel))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Variation Group List"
    }
}
