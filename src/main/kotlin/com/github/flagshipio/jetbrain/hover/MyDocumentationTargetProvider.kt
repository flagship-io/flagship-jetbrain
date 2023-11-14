package com.github.flagshipio.jetbrain.hover

import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiElement

class MyDocumentationTargetProvider : PsiDocumentationTargetProvider {

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocTarget? {
        return DocTarget(element)
    }
}
