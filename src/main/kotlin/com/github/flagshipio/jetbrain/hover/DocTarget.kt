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

class DocTarget(private val targetElement: PsiElement?) : DocumentationTarget {

    private fun getFlag(contextElement: PsiElement): Flag? {
        val flagStore = FlagStore(contextElement.project)
        val flags = flagStore.getFlags()
        val elementWithoutQuotes = contextElement.text.replace("\"", "").replace("\'", "")

        return flags.find { elementWithoutQuotes == it.name.toString() }
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
        val url = "https://app.flagship.io/env/$envID/flags-list"
        val flagKeyDetect = targetElement?.text?.replace("\"", "")

        return flagKeyHover(flag, url, flagKeyDetect)
    }

    private fun flagKeyHover(flag: Flag?, url: String, flagNotFoundKey: String?): String {
        if (flag != null) {
            return "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <style>\n" +
                    "        .rootContainer {\n" +
                    "            display: flex;\n" +
                    "            flex-direction: column;\n" +
                    "        }\n" +
                    "\n" +
                    "        .marginTop {\n" +
                    "            margin-top: 10px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .flagURL {\n" +
                    "            margin-top: 10px;\n" +
                    "            padding-bottom: 5px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "    <title></title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"rootContainer\">\n" +
                    "    <section>\n" +
                    "        <div>\n" +
                    "            <span>Flag: ${flag.name}</span>\n" +
                    "            <div class=\"marginTop\">\n" +
                    "                <span>Type: ${flag.type}</span>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div class=\"marginTop\">\n" +
                    "            <span>Description: ${flag.description}</span>\n" +
                    "        </div>\n" +
                    "        <div class=\"marginTop\">\n" +
                    "            <span>Default value: ${flag.defaultValue}</span>\n" +
                    "        </div>\n" +
                    "        <div class=\"flagURL\">\n" +
                    "            <a href=\"${url}\">Open in Flagship Platform</a>\n" +
                    "        </div>\n" +
                    "    </section>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>"
        }

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "Flag Not Found: $flagNotFoundKey - Add it with the commands\n" +
                "</body>\n" +
                "</html>"
    }
}
