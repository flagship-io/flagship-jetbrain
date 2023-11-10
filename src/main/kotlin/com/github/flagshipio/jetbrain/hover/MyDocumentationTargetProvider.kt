package com.github.flagshipio.jetbrain.hover

import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.store.FlagStore
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralValue

class MyDocumentationTargetProvider : PsiDocumentationTargetProvider {

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocTarget? {
        return DocTarget(element)
    }
}
