package com.github.flagshipio.jetbrain.toolWindow.flagsInFile

import com.github.flagshipio.jetbrain.action.goal.CopyGoalIdAction
import com.github.flagshipio.jetbrain.action.goal.CopyGoalLabelAction
import com.github.flagshipio.jetbrain.action.goal.DeleteGoalAction
import com.github.flagshipio.jetbrain.action.goal.EditGoalAction
import com.github.flagshipio.jetbrain.dataClass.FlagAnalyzed
import com.github.flagshipio.jetbrain.store.FlagsInFileStore
import com.github.flagshipio.jetbrain.toolWindow.NodeTreeStructure
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationNodeParent
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationNodeViewModel
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

class FlagsAnalyzed {

    var items: MutableList<FlagAnalyzed>? = null
    fun addItemsItem(itemsItem: FlagAnalyzed): FlagsAnalyzed {
        if (items == null) {
            items = ArrayList()
        }
        items!!.add(itemsItem)
        return this
    }
}

class FlagsInFileNode(private val intProject: Project) :
    SimpleNode() {
    private var flagNodeChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val flagsLocal = FlagsInFileStore(intProject).getFlags()

        val flagsAnalyzed = FlagsAnalyzed()
        flagsLocal.map { flagsAnalyzed.addItemsItem(it) }

        when {
            flagNodeChildren.isEmpty() && flagsAnalyzed.items != null -> {
                for (flag in flagsAnalyzed.items!!) {
                    val flagInFileViewModel = FlagInFileNodeViewModel(flag)
                    flagNodeChildren.add(FlagInFileNodeParent(flagInFileViewModel))
                }
            }
            flagsAnalyzed.items == null -> flagNodeChildren.add(RootNode("No Flag detected."))
        }

        return flagNodeChildren.toTypedArray()
    }
}

class FlagsInFileListPanel(private val myProject: Project) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var node = FlagsInFileNode(myProject)
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
        node = FlagsInFileNode(myProject)
        treeStructure = createTreeStructure()
        treeModel = StructureTreeModel(treeStructure, this)
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree.model = asyncTreeModel
    }

    private fun start(): Tree {
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree = initTree(asyncTreeModel)
        val componentsSplitter = OnePixelSplitter("FlagsInFileListSplitter", 0.33f)
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
        val copyGoalIdAction = actionManager.getAction(CopyGoalIdAction.ID)
        val copyGoalLabelAction = actionManager.getAction(CopyGoalLabelAction.ID)
        val editGoalAction = actionManager.getAction(EditGoalAction.ID)
        val deleteGoalAction = actionManager.getAction(DeleteGoalAction.ID)

        actionToolbar.targetComponent = this
        PopupHandler.installPopupMenu(
            tree,
            actionPopup.apply {
                add(copyGoalIdAction)
                add(copyGoalLabelAction)
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