package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.action.configuration.DeleteConfigurationAction
import com.github.flagshipio.jetbrain.action.configuration.EditConfigurationAction
import com.github.flagshipio.jetbrain.action.configuration.SelectConfigurationAction
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.store.ConfigurationStore
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

/*
 * FlagPanel renders the ToolWindow Flag Treeview and associated action buttons.
 */

private const val SPLITTER_PROPERTY = "BuildAttribution.Splitter.Proportion"

class Configurations {

    var items: MutableList<Configuration>? = null

    /**
     * Get totalCount
     * @return totalCount
     */


    var totalCount: BigDecimal? = null


    fun items(items: MutableList<Configuration>?): Configurations {
        this.items = items
        return this
    }

    fun addItemsItem(itemsItem: Configuration): Configurations {
        if (items == null) {
            items = ArrayList()
        }
        items!!.add(itemsItem)
        return this
    }

    /**
     * Get items
     * @return items
     */


    fun totalCount(totalCount: BigDecimal?): Configurations {
        this.totalCount = totalCount
        return this
    }

}


class ConfigurationRootNode(private val intProject: Project) :
    SimpleNode() {
    private var myChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val configurations_ = ConfigurationStore(intProject).getConfigurations()

        val configurations = Configurations()
        configurations_.map { configurations.addItemsItem(it) }

        when {
            myChildren.isEmpty() && configurations.items != null -> {
                for (configuration in configurations.items!!) {
                    val configViewModel = ConfigurationNodeViewModel(configuration)
                    myChildren.add(ConfigurationNodeParent(configViewModel))
                }
            }

            configurations.items == null -> myChildren.add(NodeBase("Flagship is not configured."))
        }

        return myChildren.toTypedArray()
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = "root"
    }
}

class ConfigurationListPanel(private val myProject: Project) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var root = ConfigurationRootNode(myProject)
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
        root = ConfigurationRootNode(myProject)
        treeStructure = createTreeStructure()
        treeModel = StructureTreeModel(treeStructure, this)
        val reviewTreeBuilder = AsyncTreeModel(treeModel, this)
        tree.model = reviewTreeBuilder
    }

    fun start(): Tree {
        val reviewTreeBuilder = AsyncTreeModel(treeModel, this)
        tree = initTree(reviewTreeBuilder)

        val componentsSplitter = OnePixelSplitter(SPLITTER_PROPERTY, 0.33f)
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
        val selectConfigurationAction = actionManager.getAction(SelectConfigurationAction.ID)
        val editConfigurationAction = actionManager.getAction(EditConfigurationAction.ID)
        val deleteConfigurationAction = actionManager.getAction(DeleteConfigurationAction.ID)

        actionToolbar.targetComponent = this
        PopupHandler.installPopupMenu(
            tree,
            actionPopup.apply {
                add(selectConfigurationAction)
                add(editConfigurationAction)
                add(deleteConfigurationAction)
            },
            ActionPlaces.POPUP
        )
    }

    init {
        tree = start()
        actions(tree)
    }
}