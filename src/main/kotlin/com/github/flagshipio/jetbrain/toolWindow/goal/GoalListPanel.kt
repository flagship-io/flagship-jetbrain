package com.github.flagshipio.jetbrain.toolWindow.goal

import com.github.flagshipio.jetbrain.action.goal.CopyLabelAction
import com.github.flagshipio.jetbrain.action.goal.DeleteGoalAction
import com.github.flagshipio.jetbrain.action.goal.EditGoalAction
import com.github.flagshipio.jetbrain.dataClass.Goal
import com.github.flagshipio.jetbrain.store.GoalStore
import com.github.flagshipio.jetbrain.toolWindow.NodeTreeStructure
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.PopupHandler
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.SideBorder
import com.intellij.ui.TreeUIHelper
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTreeStructure
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.tree.TreeUtil
import java.awt.CardLayout
import javax.swing.JPanel
import javax.swing.tree.TreeSelectionModel

class Goals {

    var items: MutableList<Goal>? = null

    fun addItemsItem(itemsItem: Goal): Goals {
        if (items == null) {
            items = ArrayList()
        }
        items!!.add(itemsItem)
        return this
    }
}

class GoalNode(private val intProject: Project) :
    SimpleNode() {
    private var goalNodeChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val goalsLocal = GoalStore(intProject).getGoals()

        val goals = Goals()
        goalsLocal.map { goals.addItemsItem(it) }

        when {
            goalNodeChildren.isEmpty() && goals.items != null -> {
                for (goal in goals.items!!) {
                    val goalViewModel = GoalNodeViewModel(goal)
                    goalNodeChildren.add(GoalNodeParent(goalViewModel))
                }
            }

            goals.items == null -> goalNodeChildren.add(RootNode("Flagship is not configured."))
        }

        return goalNodeChildren.toTypedArray()
    }
}

class GoalListPanel(private val myProject: Project) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var node = GoalNode(myProject)
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
        node = GoalNode(myProject)
        treeStructure = createTreeStructure()
        treeModel = StructureTreeModel(treeStructure, this)
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree.model = asyncTreeModel
    }

    private fun start(): Tree {
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree = initTree(asyncTreeModel)
        val componentsSplitter = OnePixelSplitter("GoalListSplitter", 0.33f)
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
        val copyLabelAction = actionManager.getAction(CopyLabelAction.ID)
        val editGoalAction = actionManager.getAction(EditGoalAction.ID)
        val deleteGoalAction = actionManager.getAction(DeleteGoalAction.ID)

        actionToolbar.targetComponent = this
        PopupHandler.installPopupMenu(
            tree,
            actionPopup.apply {
                add(copyLabelAction)
                add(editGoalAction)
                add(deleteGoalAction)
            },
            ActionPlaces.POPUP
        )
    }

    init {
        tree = start()
        actions(tree)
    }
}