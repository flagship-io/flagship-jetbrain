package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.targetingGroup

import com.github.flagshipio.jetbrain.dataClass.TargetingGroup
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class TargetingGroupNodeParent(private var targetingGroups: ArrayList<TargetingGroup>?) : SimpleNode() {
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
        if (targetingGroups == null || targetingGroups!!.size == 0) {
            children.add(RootNode("No Targeting Groups"))
            return
        }

        targetingGroups?.forEach {
            children.add(TargetingGroupListNodeParent(it))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Targeting Group List"
    }
}
