package tn.esprit.lolretrofit.models

import com.google.gson.annotations.SerializedName

data class sendmail (
    @SerializedName("email")
    var email: String,
    @SerializedName("code")
    var code: String
)
