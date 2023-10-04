package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode
import javax.swing.Icon

class NodeBase(val label: String, val labelIcon: Icon? = null) : SimpleNode() {
    override fun getChildren(): Array<SimpleNode> {
        return NO_CHILDREN
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = label
        if (labelIcon != null) {
            data.setIcon(labelIcon)
        }
    }
}
