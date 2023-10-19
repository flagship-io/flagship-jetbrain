package com.github.flagshipio.jetbrain.toolWindow.flag

import com.github.flagshipio.jetbrain.action.flag.CopyKeyAction
import com.github.flagshipio.jetbrain.action.flag.DeleteFlagAction
import com.github.flagshipio.jetbrain.action.flag.EditFlagAction
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.store.FlagStore
import com.github.flagshipio.jetbrain.toolWindow.NodeBase
import com.github.flagshipio.jetbrain.toolWindow.NodeTreeSearch
import com.github.flagshipio.jetbrain.toolWindow.NodeTreeStructure
import com.intellij.ide.projectView.PresentationData
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
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTreeStructure
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.tree.TreeUtil
import java.awt.CardLayout
import java.math.BigDecimal
import javax.swing.JPanel
import javax.swing.tree.TreeSelectionModel

class Flags {

    var items: MutableList<Flag>? = null

    fun addItemsItem(itemsItem: Flag): Flags {
        if (items == null) {
            items = ArrayList()
        }
        items!!.add(itemsItem)
        return this
    }
}

class RootNode(private val intProject: Project) :
    SimpleNode() {
    private var myChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val flagsLocal = FlagStore(intProject).getFlags()

        val flags = Flags()
        flagsLocal.map { flags.addItemsItem(it) }

        when {
            myChildren.isEmpty() && flags.items != null -> {
                for (flag in flags.items!!) {
                    val flagViewModel = FlagNodeViewModel(flag)
                    myChildren.add(FlagNodeParent(flagViewModel))
                }
            }

            flags.items == null -> myChildren.add(NodeBase("Flagship is not configured."))
        }

        return myChildren.toTypedArray()
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = "root"
    }
}

class FlagListPanel(private val myProject: Project) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var root = RootNode(myProject)
    private var treeStructure = createTreeStructure()
    private var treeModel = StructureTreeModel(treeStructure, this)
    lateinit var tree: Tree

    private fun createTreeStructure(): SimpleTreeStructure {
        return NodeTreeStructure(root)
    }

    override fun dispose() {}

    private fun initTree(model: AsyncTreeModel): Tree {
        tree = Tree(model)
        tree.isRootVisible = false
        NodeTreeSearch(tree)
        TreeUtil.installActions(tree)
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION

        return tree
    }

    fun updateNodeInfo() {
        root = RootNode(myProject)
        treeStructure = createTreeStructure()
        treeModel = StructureTreeModel(treeStructure, this)
        val reviewTreeBuilder = AsyncTreeModel(treeModel, this)
        tree.model = reviewTreeBuilder
    }

    private fun start(): Tree {
        val reviewTreeBuilder = AsyncTreeModel(treeModel, this)
        tree = initTree(reviewTreeBuilder)
        val componentsSplitter = OnePixelSplitter("FlagListSplitter", 0.33f)
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
        val copyKeyAction = actionManager.getAction(CopyKeyAction.ID)
        val editFlagAction = actionManager.getAction(EditFlagAction.ID)
        val deleteFlagAction = actionManager.getAction(DeleteFlagAction.ID)
        actionToolbar.targetComponent = this
        PopupHandler.installPopupMenu(
            tree,
            actionPopup.apply {
                add(copyKeyAction)
                add(editFlagAction)
                add(deleteFlagAction)
            },
            ActionPlaces.POPUP
        )
    }

    init {
        tree = start()
        actions(tree)
    }
}