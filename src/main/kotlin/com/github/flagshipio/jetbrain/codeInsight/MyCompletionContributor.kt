package com.github.flagshipio.jetbrain.codeInsight

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
class MyCompletionContributor : CompletionContributor() {
    override fun fillCompletionVariants(parameters: CompletionParameters, resultSet: CompletionResultSet) {
        val position = parameters.position
        val textBeforeCaret = position.text.substring(0, parameters.offset - position.textOffset)

        // Check if the text before the caret matches the desired regex pattern
        if (textBeforeCaret.matches("^.*\\bmy\\w*\\b.*$".toRegex())) {
            val suggestions = generateSuggestions()

            for (suggestion in suggestions) {
                resultSet.addElement(LookupElementBuilder.create(suggestion))
            }
        }
    }

    private fun generateSuggestions(): List<String> {
        return listOf("myOption1", "myOption2", "myOption3")
    }
}

