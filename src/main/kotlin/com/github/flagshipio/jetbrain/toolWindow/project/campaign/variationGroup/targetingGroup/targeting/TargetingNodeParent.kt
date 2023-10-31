package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.targetingGroup.targeting

import com.github.flagshipio.jetbrain.dataClass.Targeting
import com.github.flagshipio.jetbrain.dataClass.TargetingGroup
import com.github.flagshipio.jetbrain.dataClass.Targetings
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class TargetingNodeParent(private var targetings: ArrayList<Targetings>?) : SimpleNode() {
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
        if (targetings == null || targetings!!.size == 0){
            children.add(RootNode("No Targeting"))
            return
        }

        targetings?.forEach {
            val targetingNodeViewModel = TargetingNodeViewModel(it)
            children.add(TargetingListNodeParent(targetingNodeViewModel))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Targeting List"
    }
}
