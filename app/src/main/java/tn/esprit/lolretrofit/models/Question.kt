package tn.esprit.lolretrofit.models

import com.google.gson.annotations.SerializedName

data class Question (
    @SerializedName("subject")
    var subject: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("idClient")
    var idClient: String,
    @SerializedName("imageClient")
    var imageClient: String,
    @SerializedName("_id")
    var id: String
)