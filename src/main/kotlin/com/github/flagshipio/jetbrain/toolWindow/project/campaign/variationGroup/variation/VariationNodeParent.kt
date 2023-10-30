package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation

import com.github.flagshipio.jetbrain.dataClass.Variation
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class VariationNodeParent(private var variations: ArrayList<Variation>?) : SimpleNode() {
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
        if (variations == null || variations!!.size == 0){
            children.add(RootNode("No Campaigns"))
            return
        }

        variations?.forEach{
            val variationViewModel = VariationNodeViewModel(it)
            children.add(VariationListNodeParent(variationViewModel))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Variation List"
    }
}
