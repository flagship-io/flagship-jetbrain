package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.toolWindow.NodeBase
import com.intellij.ide.util.treeView.NodeRenderer
import com.intellij.ui.SimpleTextAttributes
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTree

class CustomNodeRenderer : NodeRenderer() {
    fun customizeComponent(
        tree: JTree,
        value: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ) {
        if (value is NodeBase) {
            val button = JButton("Action")
            button.addActionListener { e: ActionEvent? ->
                println("testing")
            }
            val panel = JPanel()
            panel.add(button)

            // Customize the text attributes for the node name
            if (selected) {
                append(value.name, SimpleTextAttributes.SELECTED_SIMPLE_CELL_ATTRIBUTES)
            } else {
                append(value.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            }

            // Set the renderer component
            setComponentZOrder(panel, 0)
        } else {
            super.customizeCellRenderer(tree, value, selected, expanded, leaf, row, hasFocus)
        }
    }
}