package com.github.flagshipio.jetbrain.dataClass

import com.google.gson.annotations.SerializedName

data class FileAnalyzed(
    @SerializedName("File") var file: String?,
    @SerializedName("FileURL") var fileURL: String?,
    @SerializedName("Error") var error: String?,
    @SerializedName("Results") var results: List<FlagAnalyzed>?,
) {

}