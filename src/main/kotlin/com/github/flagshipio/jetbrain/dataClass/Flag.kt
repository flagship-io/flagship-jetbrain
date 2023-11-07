package com.github.flagshipio.jetbrain.dataClass

import com.google.gson.annotations.SerializedName

data class Flag(
    var id: String?,
    var name: String?,
    var type: String?,
    var description: String?,
    @SerializedName("default_value") var defaultValue: String? = null
) {

}