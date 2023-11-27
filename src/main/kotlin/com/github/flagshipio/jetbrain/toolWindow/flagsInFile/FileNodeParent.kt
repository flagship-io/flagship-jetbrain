package com.github.flagshipio.jetbrain.toolWindow.flagsInFile

import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class FileNodeParent(private var viewModel: FileNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val file get() = viewModel.file

    override fun getChildren(): Array<SimpleNode> {
        if (children.isNotEmpty()) {
            children = ArrayList()
        }
        addChildren()
        return children.toTypedArray()
    }

    private fun addChildren() {
        file.results?.map {
            it.flagFile = file.file
            val flagViewModel = FlagInFileNodeViewModel(it)
            children.add(FlagInFileNodeParent(flagViewModel))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText =
            viewModel.fileName?.split("/")?.last() + " - " + (viewModel.flagInFile?.count() ?: 0) + " Flag(s) detected"
    }
}
