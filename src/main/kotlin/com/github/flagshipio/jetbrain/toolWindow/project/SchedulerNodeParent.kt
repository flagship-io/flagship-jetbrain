package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class SchedulerNodeParent(private val viewModel: SchedulerNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()

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
        children.add(RootNode("Start Date: ${viewModel.schedulerStartDate}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Stop Date ${viewModel.schedulerStopDate}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("timezone: ${viewModel.schedulerTimezone}", Debugger.Db_muted_breakpoint))
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Scheduler"
    }
}
