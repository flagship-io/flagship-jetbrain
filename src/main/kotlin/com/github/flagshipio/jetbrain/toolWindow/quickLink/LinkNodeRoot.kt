package com.github.flagshipio.jetbrain.toolWindow.quickLink

import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class LinkNodeRoot :
    SimpleNode() {
    private var myChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        myChildren.add(
            LinkNodeBase(
                "Flags",
                "https://example.com"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Segments",
                "https://example.com"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Users",
                "https://example.com"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Debugger",
                "https://example.com"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Experiments",
                "https://example.com"
            )
        )


        return myChildren.toTypedArray()
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = "root"
    }
}
