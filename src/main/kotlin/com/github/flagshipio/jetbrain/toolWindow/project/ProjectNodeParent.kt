package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.CampaignNodeParent
import com.intellij.icons.AllIcons
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

const val NAME_PREFIX = "Name:"

class ProjectNodeParent(private var viewModel: ProjectNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val name_: String? get() = viewModel.project.name

    val project get() = viewModel.project

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
        children.add(RootNode("Id: ${viewModel.projectId}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("$NAME_PREFIX ${viewModel.projectName}", Debugger.Db_muted_breakpoint))
        children.add(CampaignNodeParent(project.campaign))
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Project: "+viewModel.projectName
        data.setIcon(AllIcons.Nodes.Folder)
    }
}
