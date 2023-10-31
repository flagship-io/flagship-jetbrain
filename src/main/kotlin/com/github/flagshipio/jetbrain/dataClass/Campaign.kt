package com.github.flagshipio.jetbrain.dataClass

import com.google.gson.annotations.SerializedName

data class Campaign(
    var id: String?,
    var name: String?,
    var type: String?,
    var description: String?,
    @SerializedName("project_id") var projectID: String,
    var status: String?,
    @SerializedName("variation_groups") val variationGroups: ArrayList<VariationGroup>,
    val scheduler: Scheduler?,
) {
}

data class Scheduler(
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("stop_date") val stopDate: String?,
    val timezone: String?,
){
}

data class VariationGroup(
    val id: String?,
    val name: String,
    val variations: ArrayList<Variation>?,
    val targeting: Targeting?,
){
}

data class Targeting(
    @SerializedName("targeting_groups") val targetingGroups: ArrayList<TargetingGroup>?,
){
}

data class TargetingGroup(
    val targetings: ArrayList<Targetings>?,
){
}

data class Targetings(
    val key: String,
    val operator: String?,
    val value: String?,
){
}

data class Variation(
    val id: String?,
    val name: String,
    val reference: Boolean?,
    val allocation: Number?,
    val modifications: Modification?,
){
}

data class Modification(
    val type: String?,
    val value: Any?,
){

}