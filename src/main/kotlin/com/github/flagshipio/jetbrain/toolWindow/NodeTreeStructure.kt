package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTreeStructure

class NodeTreeStructure(private val myRootElement: SimpleNode) : SimpleTreeStructure() {
    override fun getRootElement(): SimpleNode {
        return myRootElement
    }
}
