package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.dataClass.Campaign
import com.github.flagshipio.jetbrain.toolWindow.RootNode
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
        project.campaign?.forEach {
                val campaignViewModel = CampaignNodeViewModel(it)
                children.add(CampaignNodeParent(campaignViewModel))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Project: "+viewModel.projectName
    }
}
