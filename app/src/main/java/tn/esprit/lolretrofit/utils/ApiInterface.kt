package tn.esprit.lolretrofit.utils

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.User

interface ApiInterface {

    @POST("loginClient")
    fun seConnecter(@Body info: RequestBody): Call<User>

    @POST("createuser")
    fun SignIn(@Body info: RequestBody): Call<User>

    @PUT("updateuser/{id}")
    fun UpdateUser(@Body info: RequestBody,@Path("id") id : String): Call<User>

    @GET("allquestions")
    fun AllQuestion(): Call<MutableList<Question>>

    @GET("getuserEmail/{email}")
    fun getuserbyemail( @Path("email") email : String): Call<MutableList<User>>
    companion object {

        var BASE_URL = "http://192.168.174.107:3000/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}