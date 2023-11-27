package com.github.flagshipio.jetbrain.toolWindow.configuration

import com.github.flagshipio.jetbrain.action.configuration.DeleteConfigurationAction
import com.github.flagshipio.jetbrain.action.configuration.EditConfigurationAction
import com.github.flagshipio.jetbrain.action.configuration.SelectConfigurationAction
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.store.ConfigurationStore
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

class Configurations {

    var items: MutableList<Configuration>? = null

    fun addItemsItem(itemsItem: Configuration): Configurations {
        if (items == null) {
            items = ArrayList()
        }
        items!!.add(itemsItem)
        return this
    }
}


class ConfigurationNode(private val intProject: Project) :
    SimpleNode() {
    private var configurationNodeChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val configurationsLocal = ConfigurationStore(intProject).getConfigurations()

        val configurations = Configurations()
        configurationsLocal.map { configurations.addItemsItem(it) }

        when {
            configurationNodeChildren.isEmpty() && configurations.items != null -> {
                for (configuration in configurations.items!!) {
                    val configViewModel = ConfigurationNodeViewModel(configuration)
                    configurationNodeChildren.add(ConfigurationNodeParent(configViewModel))
                }
            }

            configurations.items == null -> configurationNodeChildren.add(RootNode("Flagship is not configured."))
        }

        return configurationNodeChildren.toTypedArray()
    }
}

class ConfigurationListPanel(private val myProject: Project) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var node = ConfigurationNode(myProject)
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
        node = ConfigurationNode(myProject)
        treeStructure = createTreeStructure()
        treeModel = StructureTreeModel(treeStructure, this)
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree.model = asyncTreeModel
    }

    private fun start(): Tree {
        val asyncTreeModel = AsyncTreeModel(treeModel, this)
        tree = initTree(asyncTreeModel)

        val componentsSplitter = OnePixelSplitter("ConfigurationListSplitter", 0.33f)
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