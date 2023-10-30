package com.github.flagshipio.jetbrain.dataClass

data class Project(
    var id: String?,
    var name: String?,
    var campaign: ArrayList<Campaign>?,
) {
}