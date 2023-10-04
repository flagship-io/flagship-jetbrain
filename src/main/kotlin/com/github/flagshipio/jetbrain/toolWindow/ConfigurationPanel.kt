package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.action.CopyKeyAction
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.dataClass.Feature
import com.github.flagshipio.jetbrain.messaging.FlagNotifier
import com.github.flagshipio.jetbrain.messaging.MessageBusService
import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.github.flagshipio.jetbrain.store.FeatureStore
import com.google.gson.annotations.SerializedName
import com.intellij.ide.projectView.PresentationData
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
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
import com.intellij.ui.components.JBPanel
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.SimpleTreeStructure
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.UIUtil.invokeLaterIfNeeded
import com.intellij.util.ui.tree.TreeUtil
import java.awt.CardLayout
import java.math.BigDecimal
import java.util.*
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


class RootNode1(private val intProject: Project) :
    SimpleNode() {
    private var myChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        val configurations_ = ConfigurationStore(intProject).getConfiguration(intProject)

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

class ConfigurationPanel(private val myProject: Project, messageBusService: MessageBusService) :
    SimpleToolWindowPanel(false, false), Disposable {
    private var root = RootNode1(myProject)
    private var treeStructure = createTreeStructure()
    private var treeModel = StructureTreeModel(treeStructure, this)
    lateinit var tree: Tree

    private fun createTreeStructure(): SimpleTreeStructure {
        return FlagTreeStructure(root)
    }

    override fun dispose() {}

    private fun initTree(model: AsyncTreeModel): Tree {
        tree = Tree(model)
        tree.isRootVisible = false
        FlagTreeSearch(tree)
        TreeUtil.installActions(tree)
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION

        return tree
    }

    fun updateNodeInfo() {
        root = RootNode1(myProject)
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
        componentsSplitter.firstComponent = JBPanel<BasePanel>(CardLayout()).apply {
            add(ScrollPaneFactory.createScrollPane(tree, SideBorder.NONE), "Tree")
        }
        setContent(componentsSplitter)
        return tree
    }

    fun actions(tree: Tree) {
        val actionManager: ActionManager = ActionManager.getInstance()
        val actionGroup = DefaultActionGroup()
        val actionPopup = DefaultActionGroup()
        val actionToolbar: ActionToolbar = actionManager.createActionToolbar("ACTION_TOOLBAR", actionGroup, true)
        toolbar = actionToolbar.component
        val copyKeyAction = actionManager.getAction(CopyKeyAction.ID)
        actionToolbar.targetComponent = this
        PopupHandler.installPopupMenu(
            tree,
            actionPopup.apply {
                add(copyKeyAction)
            },
            ActionPlaces.POPUP
        )
    }

    init {

            tree = start()
            actions(tree)

        var start = false
        if (!this::tree.isInitialized) {
            start = true
        }

        try {
            myProject.messageBus.connect().subscribe(
                messageBusService.flagsUpdatedTopic,
                object : FlagNotifier {
                    override fun notify(isConfigured: Boolean, flag: String, rebuild: Boolean) {
                            if (start) {
                                tree = start()
                                actions(tree)
                        } else {
                            val notification = Notification(
                                "ProjectOpenNotification",
                                "Flagship",
                                String.format("Flagship is not configured"),
                                NotificationType.WARNING
                            )
                            notification.notify(myProject)
                        }
                    }

                    override fun reinit() {
                        if (start) {
                            tree = start()
                            actions(tree)
                        }
                        invokeLaterIfNeeded {
                            updateNodeInfo()
                        }
                    }
                }
            )
        } catch (err: Error) {
            println(err)
        }
    }
}