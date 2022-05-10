package tn.esprit.lolretrofit.models

import com.google.gson.annotations.SerializedName


data class Message (

    @SerializedName("message")
    var message: String,
    @SerializedName("objectt")
    var objectt: String,
    @SerializedName("from")
    var from: String,

    @SerializedName("to")
    var to: String,
    @SerializedName("_id")
    var id: String
)