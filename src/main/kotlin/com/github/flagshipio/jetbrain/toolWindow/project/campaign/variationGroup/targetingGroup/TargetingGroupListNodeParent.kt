package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.targetingGroup

import com.github.flagshipio.jetbrain.dataClass.TargetingGroup
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.github.flagshipio.jetbrain.toolWindow.project.NAME_PREFIX
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation.VariationNodeParent
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.targetingGroup.targeting.TargetingNodeParent
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class TargetingGroupListNodeParent(private var targetingGroup: TargetingGroup) : SimpleNode() {
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
        children.add(TargetingNodeParent(targetingGroup.targetings))
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Targeting Group"
    }
}
