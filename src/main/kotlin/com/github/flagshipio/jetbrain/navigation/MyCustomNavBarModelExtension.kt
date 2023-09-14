package com.github.flagshipio.jetbrain.navigation

import com.intellij.ide.navigationToolbar.NavBarModelExtension
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement


class MyCustomNavBarModelExtension : NavBarModelExtension {

    override fun getPresentableText(`object`: Any?): String {
        return "Hello"
    }


    override fun getParent(psiElement: PsiElement?): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun adjustElement(psiElement: PsiElement): PsiElement? {
        TODO("Not yet implemented")
    }

    override fun additionalRoots(project: Project?): MutableCollection<VirtualFile> {
        TODO("Not yet implemented")
    }
}

