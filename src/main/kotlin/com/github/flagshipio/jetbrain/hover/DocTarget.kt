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

class DocTarget(private val targetElement: PsiElement?) : DocumentationTarget{

    private fun getFlag(contextElement: PsiElement): Flag? {
        val flagStore = FlagStore(contextElement.project)
        val flags = flagStore.getFlags()

        return flags.find { contextElement.text.contains(it.name.toString()) }
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

    override fun computeDocumentationHint(): String? {
        val flag = targetElement?.let { getFlag(it) } ?: return null

        val flagViewModel = buildMap {
            put("name", flag.name)
            put("description", flag.description)
            put("on", true)
            put("url", "url")
        }

        val template = PebbleEngine.Builder().build()
        val temp = template.getTemplate("htmlTemplates/flagKeyHover.html")
        val writer = StringWriter()
        temp.evaluate(writer, mapOf("flag" to flagViewModel))
        return writer.toString()
    }

    override fun computeDocumentation(): DocumentationResult? {
        val flag = targetElement?.let { getFlag(it) } ?: return null
        val envID = getCurrentConfigurationEnvID(targetElement) ?: return null

        // Construct view models in kotlin to avoid logic operations in pebble (pain!)
        val flagViewModel = buildMap {
            put("name", flag.name)
            put("description", flag.description)
            put("on", true)
            put("url", "https://app.flagship.io/env/$envID/flags-list")
        }

        val template = PebbleEngine.Builder().build()
        val temp = template.getTemplate("htmlTemplates/flagKeyHover.html")
        val writer = StringWriter()
        temp.evaluate(writer, mapOf("flag" to flagViewModel))
        return DocumentationResult.Companion.documentation(writer.toString())
    }
}