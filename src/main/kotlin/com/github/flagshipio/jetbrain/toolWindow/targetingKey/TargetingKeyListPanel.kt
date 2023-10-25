package com.github.flagshipio.jetbrain.toolWindow.targetingKey

import com.github.flagshipio.jetbrain.action.goal.CopyLabelAction
import com.github.flagshipio.jetbrain.action.goal.DeleteGoalAction
import com.github.flagshipio.jetbrain.action.goal.EditGoalAction
import com.github.flagshipio.jetbrain.action.targetingKey.CopyTargetingKeyNameAction
import com.github.flagshipio.jetbrain.action.targetingKey.DeleteTargetingKeyAction
import com.github.flagshipio.jetbrain.action.targetingKey.EditTargetingKeyAction
import com.github.flagshipio.jetbrain.dataClass.TargetingKey
import com.github.flagshipio.jetbrain.store.TargetingKeyStore
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

class TargetingKeys {

    var items: MutableList<TargetingKey>? = null

    fun addItemsItem(itemsItem: TargetingKey): TargetingKeys {
        if (items == null) {
            items = ArrayList()
        }
        items!!.add(itemsItem)
        return this
    }
}

class TargetingKeyNode(private val intProject: Project) :
    SimpleNode() {
    private var targetingKeyNodeChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val targetingKeysLocal = TargetingKeyStore(intProject).getTargetingKeys()

        val targetingKeys = TargetingKeys()
        targetingKeysLocal.map { targetingKeys.addItemsItem(it) }

        when {
            targetingKeyNodeChildren.isEmpty() && targetingKeys.items != null -> {
                for (targetingKey in targetingKeys.items!!) {
                    val targetingKeyViewModel = TargetingKeyNodeViewModel(targetingKey)
                    targetingKeyNodeChildren.add(TargetingKeyNodeParent(targetingKeyViewModel))
                }
            }

            targetingKeys.items == null -> targetingKeyNodeChildren.add(RootNode("Flagship is not configured."))
        }

        return targetingKeyNodeChildren.toTypedArray()
    }
}

class TargetingKeyListPanel(private val myProject: Project) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var node = TargetingKeyNode(myProject)
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
        node = TargetingKeyNode(myProject)
        treeStructure = createTreeStructure()
        treeModel = StructureTreeModel(treeStructure, this)
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree.model = asyncTreeModel
    }

    private fun start(): Tree {
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree = initTree(asyncTreeModel)
        val componentsSplitter = OnePixelSplitter("TargetingKeyListSplitter", 0.33f)
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
        val copyTargetingKeyNameAction = actionManager.getAction(CopyTargetingKeyNameAction.ID)
        val editTargetingKeyAction = actionManager.getAction(EditTargetingKeyAction.ID)
        val deleteTargetingKeyAction = actionManager.getAction(DeleteTargetingKeyAction.ID)

        actionToolbar.targetComponent = this
        PopupHandler.installPopupMenu(
            tree,
            actionPopup.apply {
                add(copyTargetingKeyNameAction)
                add(editTargetingKeyAction)
                add(deleteTargetingKeyAction)
            },
            ActionPlaces.POPUP
        )
    }

    init {
        tree = start()
        actions(tree)
    }
}