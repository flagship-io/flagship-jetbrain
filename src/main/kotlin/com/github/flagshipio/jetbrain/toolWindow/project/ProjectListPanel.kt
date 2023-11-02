package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.action.campaign.CopyCampaignIdAction
import com.github.flagshipio.jetbrain.action.campaign.CopyCampaignNameAction
import com.github.flagshipio.jetbrain.action.campaign.DeleteCampaignAction
import com.github.flagshipio.jetbrain.action.campaign.SwitchCampaignAction
import com.github.flagshipio.jetbrain.action.project.*
import com.github.flagshipio.jetbrain.store.ProjectStore
import com.github.flagshipio.jetbrain.toolWindow.NodeTreeStructure
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.*
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTreeStructure
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.tree.TreeUtil
import java.awt.CardLayout
import javax.swing.JPanel
import javax.swing.tree.TreeSelectionModel

class Projects {

    var items: MutableList<com.github.flagshipio.jetbrain.dataClass.Project>? = null

    fun addItemsItem(itemsItem: com.github.flagshipio.jetbrain.dataClass.Project): Projects {
        if (items == null) {
            items = ArrayList()
        }
        items!!.add(itemsItem)
        return this
    }
}

class ProjectNode(private val intProject: Project) :
    SimpleNode() {
    private var projectNodeChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val projectsLocal = ProjectStore(intProject).getProjects()
        println(projectsLocal)

        val projects = Projects()
        projectsLocal.map { projects.addItemsItem(it) }

        when {
            projectNodeChildren.isEmpty() && projects.items != null -> {
                for (project in projects.items!!) {
                    val projectViewModel = ProjectNodeViewModel(project)
                    projectNodeChildren.add(ProjectNodeParent(projectViewModel))
                }
            }

            projects.items == null -> projectNodeChildren.add(RootNode("Flagship is not configured."))
        }

        return projectNodeChildren.toTypedArray()
    }
}

class ProjectListPanel(private val myProject: Project) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var node = ProjectNode(myProject)
    private var treeStructure = createTreeStructure()
    private var treeModel = StructureTreeModel(treeStructure, this)
    var tree: Tree

    private fun createTreeStructure(): SimpleTreeStructure {
        return NodeTreeStructure(node)
    }

    override fun dispose() {}

    private fun initTree(model: AsyncTreeModel): Tree {
        tree = Tree(model)
        tree.isRootVisible = false
        TreeUIHelper.getInstance().installTreeSpeedSearch(tree)
        TreeUtil.installActions(tree)
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION

        return tree
    }

    fun updateNodeInfo() {
        node = ProjectNode(myProject)
        treeStructure = createTreeStructure()
        treeModel = StructureTreeModel(treeStructure, this)
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree.model = asyncTreeModel
    }

    private fun start(): Tree {
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree = initTree(asyncTreeModel)
        val componentsSplitter = OnePixelSplitter("ProjectListSplitter", 0.33f)
        componentsSplitter.setHonorComponentsMinimumSize(true)
        componentsSplitter.firstComponent = JPanel(CardLayout()).apply {
            add(ScrollPaneFactory.createScrollPane(tree, SideBorder.NONE), "Tree")
        }
        setContent(componentsSplitter)

        return tree
    }

    private fun actions(tree: Tree) {
        val actionManager: ActionManager = ActionManager.getInstance()
        val actionGroup = DefaultActionGroup()
        val actionPopup = DefaultActionGroup()
        val actionToolbar: ActionToolbar = actionManager.createActionToolbar("ACTION_TOOLBAR", actionGroup, true)
        toolbar = actionToolbar.component
        val copyProjectIdAction = actionManager.getAction(CopyProjectIdAction.ID)
        val copyProjectNameAction = actionManager.getAction(CopyProjectNameAction.ID)
        val editProjectAction = actionManager.getAction(EditProjectAction.ID)
        val deleteProjectAction = actionManager.getAction(DeleteProjectAction.ID)
        val switchProjectAction = actionManager.getAction(SwitchProjectAction.ID)

        val copyCampaignIdAction = actionManager.getAction(CopyCampaignIdAction.ID)
        val copyCampaignNameAction = actionManager.getAction(CopyCampaignNameAction.ID)
        val deleteCampaignAction = actionManager.getAction(DeleteCampaignAction.ID)
        val switchCampaignAction = actionManager.getAction(SwitchCampaignAction.ID)

        actionToolbar.targetComponent = this
        PopupHandler.installPopupMenu(
            tree,
            actionPopup.apply {
                //project
                add(copyProjectIdAction)
                add(copyProjectNameAction)
                add(editProjectAction)
                add(deleteProjectAction)
                add(switchProjectAction)
                //campaign
                add(copyCampaignIdAction)
                add(copyCampaignNameAction)
                add(deleteCampaignAction)
                add(switchCampaignAction)
            },
            ActionPlaces.POPUP
        )
    }

    init {
        tree = start()
        actions(tree)
    }
}