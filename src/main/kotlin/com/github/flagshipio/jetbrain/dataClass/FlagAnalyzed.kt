package com.github.flagshipio.jetbrain.dataClass

import com.google.gson.annotations.SerializedName

data class FlagAnalyzed(
    @SerializedName("FlagKey") var flagKey: String?,
    @SerializedName("FlagDefaultValue") var flagDefaultValue: String?,
    @SerializedName("FlagType") var flagType: String?,
    @SerializedName("LineNumber") var lineNumber: Number?,
    @SerializedName("CodeLines") var codeLines: String?,
    @SerializedName("CodeLineHighlight") var codeLineHighlight: String?,
    @SerializedName("CodeLineURl") var codeLineURL: String?,
) {

}