package com.github.flagshipio.jetbrain.dataClass
import com.google.gson.annotations.SerializedName

data class Feature(var id: String?, var name: String?, var type: String?, var description: String?, var source: String?, @SerializedName("default_value") var defaultValue: String? = null) {

}