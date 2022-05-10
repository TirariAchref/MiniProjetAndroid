package tn.esprit.lolretrofit.models

import com.google.gson.annotations.SerializedName

data class Reponse (

    @SerializedName("description")
    var description: String,
    @SerializedName("idUser")
    var idUser: String,
    @SerializedName("idQuestion")
    var idQuestion: String,
    @SerializedName("nameUser")
    var nameUser: String,

    @SerializedName("_id")
    var id: String
)