package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode
import javax.swing.Icon

class LinkNodeBase(private var label: String, val url: String, var labelIcon: Icon? = null) : SimpleNode() {
    override fun getChildren(): Array<SimpleNode> {
        return NO_CHILDREN
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = label
        data.setIcon(AllIcons.Ide.External_link_arrow)
    }
}
