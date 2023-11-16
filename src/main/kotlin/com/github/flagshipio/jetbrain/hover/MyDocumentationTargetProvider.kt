package com.github.flagshipio.jetbrain.hover

import com.intellij.openapi.editor.Document
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement

class MyDocumentationTargetProvider : PsiDocumentationTargetProvider {

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocTarget? {
        if (element.containingFile != null) {
            val document: Document? = PsiDocumentManager.getInstance(element.project).getDocument(element.containingFile)
            if (document != null) {
                val offset = element.textOffset
                val lineStartOffset: Int = document.getLineStartOffset(document.getLineNumber(offset))
                val lineText = document.text.substring(lineStartOffset, offset)

                if (isGetFlagFunctionHover(lineText)) {
                    return DocTarget(element)
                }
            }
            return null
        }
        return null
    }
}

fun isGetFlagFunctionHover(linePrefix: String): Boolean {
    return (Regex("getFlag\\(\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getFlag\\(\\s*[\\w\\-\\_]*['\"]").containsMatchIn(
        linePrefix
    )) ||
            (Regex("getModification\\(\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getModification\\(\\s*[\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("get_modification\\(\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("get_modification\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("GetModification\\(String|Number|Bool|Object|Array\\)\\(\\s*[\\w\\-\\_]*").containsMatchIn(
                linePrefix
            ) && !Regex("GetModification\\(String|Number|Bool|Object|Array\\)\\(\\s*[\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("GetModification\\(\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("GetModification\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("GetFlag\\(\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("GetFlag\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("useFsFlag\\(\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("useFsFlag\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("getModification:\\s*@\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getModification:\\s*@\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("getFlagWithKey:\\s*@\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getFlagWithKey:\\s*@\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("getFlag\\(\\s*key\\s*:\\s*[\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getFlag\\(\\s*key\\s*:\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            ))
}


