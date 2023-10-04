package com.github.flagshipio.jetbrain.dataClass

import com.google.gson.annotations.SerializedName

data class Configuration(var name: String?, @SerializedName("client_id") var clientID: String?, @SerializedName("client_secret") var clientSecret: String?, @SerializedName("account_id") var accountID: String?, @SerializedName("account_environment_id") var accountEnvironmentID: String?) {

}