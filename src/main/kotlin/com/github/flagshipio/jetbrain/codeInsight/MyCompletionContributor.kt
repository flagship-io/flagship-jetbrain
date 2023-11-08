package com.github.flagshipio.jetbrain.codeInsight

import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.store.FlagStore
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.editor.Document

class MyCompletionContributor : CompletionContributor() {
    override fun fillCompletionVariants(parameters: CompletionParameters, resultSet: CompletionResultSet) {
        val flagStore: FlagStore? = parameters.editor.project?.let { FlagStore(it) }
        val document: Document = parameters.editor.document
        val offset = parameters.offset
        val lineStartOffset: Int = document.getLineStartOffset(document.getLineNumber(offset))
        val lineText = document.text.substring(lineStartOffset, offset)

        if (isGetFlagFunction(lineText)) {
            val suggestions = flagStore?.let { generateSuggestions(it.getFlags()) }

            if (suggestions != null) {
                for (suggestion in suggestions) {
                    resultSet.addElement(LookupElementBuilder.create(suggestion))
                }
            }
        }
    }

    private fun generateSuggestions(flagList: List<Flag>): List<String> {
        return flagList.map {
            it.name!!
        }
    }
}

fun isGetFlagFunction(linePrefix: String): Boolean {
    return (Regex("getFlag\\(\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getFlag\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
        linePrefix
    )) ||
            (Regex("getModification\\(\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getModification\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("get_modification\\(\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("get_modification\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("GetModification\\(String|Number|Bool|Object|Array\\)\\(\\s*['\"][\\w\\-\\_]*").containsMatchIn(
                linePrefix
            ) && !Regex("GetModification\\(String|Number|Bool|Object|Array\\)\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("GetModification\\(\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("GetModification\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("GetFlag\\(\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("GetFlag\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("useFsFlag\\(\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("useFsFlag\\(\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("getModification:\\s*@\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getModification:\\s*@\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("getFlagWithKey:\\s*@\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getFlagWithKey:\\s*@\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            )) ||
            (Regex("getFlag\\(\\s*key\\s*:\\s*['\"][\\w\\-\\_]*").containsMatchIn(linePrefix) && !Regex("getFlag\\(\\s*key\\s*:\\s*['\"][\\w\\-\\_]*['\"]").containsMatchIn(
                linePrefix
            ))
}



