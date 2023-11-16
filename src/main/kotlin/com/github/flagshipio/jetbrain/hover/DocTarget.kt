package com.github.flagshipio.jetbrain.hover

import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.github.flagshipio.jetbrain.store.FlagStore
import com.intellij.icons.AllIcons
import com.intellij.model.Pointer
import com.intellij.platform.backend.documentation.DocumentationResult
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.presentation.TargetPresentation
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.createSmartPointer
import io.pebbletemplates.pebble.PebbleEngine
import java.io.StringWriter

class DocTarget(private val targetElement: PsiElement?) : DocumentationTarget {

    private fun getFlag(contextElement: PsiElement): Flag? {
        val flagStore = FlagStore(contextElement.project)
        val flags = flagStore.getFlags()
        val element = contextElement.text.replace("\"", "")

        return flags.find { element == it.name.toString() }
    }

    private fun getCurrentConfigurationEnvID(contextElement: PsiElement): String? {
        val configurationStore = ConfigurationStore(contextElement.project)
        val currentConfig = configurationStore.getCurrentConfiguration()

        if (currentConfig != null) {
            return currentConfig.accountEnvironmentID
        }
        return null
    }

    override fun computePresentation(): TargetPresentation {
        return TargetPresentation.builder("Flag").icon(AllIcons.Nodes.Field).presentation()
    }

    override fun createPointer(): Pointer<out DocumentationTarget> {
        val originalElementPtr = targetElement?.createSmartPointer()
        return Pointer {
            DocTarget(originalElementPtr?.dereference())
        }
    }

    override fun computeDocumentationHint(): String {
        return docWriter(targetElement)
    }

    override fun computeDocumentation(): DocumentationResult {
        return DocumentationResult.Companion.documentation(docWriter(targetElement))
    }

    private fun docWriter(targetElement: PsiElement?): String {
        val flag = targetElement?.let { getFlag(it) }
        val envID = targetElement?.let { getCurrentConfigurationEnvID(it) }
        val template = PebbleEngine.Builder().build()
        val writer = StringWriter()

        if (flag != null) {
            val flagViewModel = buildMap {
                put("name", flag.name)
                put("description", flag.description)
                put("type", flag.type)
                put("default_value", flag.defaultValue)
                put("url", "https://app.flagship.io/env/$envID/flags-list")
            }
            val temp = template.getTemplate("htmlTemplates/flagKeyHover.html")
            temp.evaluate(writer, mapOf("flag" to flagViewModel))
        } else {
            val flagViewModel = buildMap {
                if (targetElement != null) {
                    put("name", targetElement.text.replace("\"", ""))
                }
            }
            val temp = template.getTemplate("htmlTemplates/flagKeyNotFound.html")
            temp.evaluate(writer, mapOf("flag" to flagViewModel))
        }

        return writer.toString()
    }
}
