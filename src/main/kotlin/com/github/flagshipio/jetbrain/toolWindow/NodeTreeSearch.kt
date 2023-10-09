package com.github.flagshipio.jetbrain.toolWindow


import com.intellij.ui.TreeSpeedSearch
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreePath

class NodeTreeSearch(tree: JTree) : TreeSpeedSearch(tree) {
    override fun getElementText(element: Any?): String? {
        val path: TreePath = element as TreePath
        val node = path.lastPathComponent as DefaultMutableTreeNode
        val objectNode = node.userObject
        return objectNode.toString()
    }
}
