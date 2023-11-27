package com.github.flagshipio.jetbrain.toolWindow.quickLink

import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class LinkNodeRoot :
    SimpleNode() {
    private var myChildren: MutableList<SimpleNode> = ArrayList()

    override fun getChildren(): Array<SimpleNode> {
        myChildren.add(
            LinkNodeBase(
                "Set up the credentials",
                "https://flagship.zendesk.com/hc/en-us/articles/4499017687708--Acting-on-your-account-remotely"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Manage configurations",
                "https://docs.developers.flagship.io/docs/manage-configurations-jetbrain"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Manage flagship resources",
                "https://docs.developers.flagship.io/docs/manage-flagship-resource-jetbrain"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Hover feature",
                "https://docs.developers.flagship.io/docs/hover-feature-jetbrain"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Flags in file feature",
                "https://docs.developers.flagship.io/docs/flags-in-file-feature-jetbrain"
            )
        )
        myChildren.add(
            LinkNodeBase(
                "Autocomplete feature",
                "https://docs.developers.flagship.io/docs/autocomplete-feature-jetbrain"
            )
        )


        return myChildren.toTypedArray()
    }

    override fun update(data: PresentationData) {
        super.update(data)
        data.presentableText = "root"
    }
}
