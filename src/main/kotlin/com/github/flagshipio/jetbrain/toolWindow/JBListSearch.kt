package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.ui.DocumentAdapter
import com.intellij.ui.SearchTextField
import com.intellij.ui.components.JBList
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.util.ui.tree.TreeUtil
import java.awt.BorderLayout
import javax.swing.DefaultListModel
import javax.swing.JPanel
import javax.swing.JTree
import javax.swing.event.DocumentEvent

class JBListSearch(private val jbList: JBList<String>) {
    private val model = DefaultListModel<String>()

    init {
        jbList.model = model
    }

    fun performSearch(query: String) {
        model.clear()

        // Implement your search logic here and add matching items to the model
        // For example:
        // for (item in yourDataList) {
        //     if (item.contains(query)) {
        //         model.addElement(item)
        //     }
        // }
    }
}

fun main() {
    val jbList = JBList<String>()
    val searchTextField = SearchTextField()
    val searchPanel = JPanel(BorderLayout())
    searchPanel.add(searchTextField, BorderLayout.CENTER)

    val listSearch = JBListSearch(jbList)

    searchTextField.addDocumentListener(object : DocumentAdapter() {
        override fun textChanged(e: DocumentEvent) {
            listSearch.performSearch(searchTextField.text)
        }
    })

    val panel = JPanel(BorderLayout())
    panel.add(searchPanel, BorderLayout.NORTH)
    panel.add(jbList, BorderLayout.CENTER)

    // Add the panel to your UI
}


