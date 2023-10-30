package com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.variation.modification

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.google.gson.internal.LinkedTreeMap
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class ModificationValueNodeParent(private var modificationValues: LinkedTreeMap<*,*>?) : SimpleNode() {
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
        if (modificationValues == null || modificationValues!!.size == 0){
            children.add(RootNode("No Modification Value"))
            return
        }

        modificationValues?.forEach{
            children.add(RootNode("${it.key}: ${it.value}", Debugger.Db_muted_breakpoint))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Value"
    }
}
