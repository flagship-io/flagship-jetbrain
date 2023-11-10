package com.github.flagshipio.jetbrain.codeInsight

import com.github.flagshipio.jetbrain.store.FlagStore
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.Document

class MyCompletionContributor : CompletionContributor() {
    override fun fillCompletionVariants(parameters: CompletionParameters, resultSet: CompletionResultSet) {
        val flagStore: FlagStore? = parameters.editor.project?.let { FlagStore(it) }
        val document: Document = parameters.editor.document
        val offset = parameters.offset
        val lineStartOffset: Int = document.getLineStartOffset(document.getLineNumber(offset))
        val lineText = document.text.substring(lineStartOffset, offset)

        if (isGetFlagFunction(lineText)) {
            val suggestions = flagStore?.getFlags()

            if (suggestions != null) {
                for (suggestion in suggestions) {
                    resultSet.restartCompletionOnAnyPrefixChange();
                    suggestion.name?.let { LookupElementBuilder.create(it).withTypeText(suggestion.description).withIcon(AllIcons.Debugger.Db_no_suspend_method_breakpoint) }
                        ?.let { resultSet.addElement(it) }
                }
            }
        }
    }

    private fun isGetFlagFunction(linePrefix: String): Boolean {
        println(linePrefix)
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

}

