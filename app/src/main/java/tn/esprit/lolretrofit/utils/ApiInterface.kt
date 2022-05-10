package tn.esprit.lolretrofit.utils

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import tn.esprit.lolretrofit.models.Question
import tn.esprit.lolretrofit.models.Reponse
import tn.esprit.lolretrofit.models.User
import tn.esprit.lolretrofit.models.sendmail
import java.util.concurrent.TimeUnit


interface ApiInterface {

    @POST("loginClient")
    fun seConnecter(@Body info: RequestBody): Call<User>

    @POST("sendmail")
    fun sendmail(@Body info: RequestBody): Call<sendmail>

    @POST("createuser")
    fun SignIn(@Body info: RequestBody): Call<User>

    @PUT("updateuser/{id}")
    fun UpdateUser(@Body info: RequestBody,@Path("id") id : String): Call<User>

    @PUT("updateuserpass/{id}")
    fun updateusernotpass(@Body info: RequestBody,@Path("id") id : String): Call<User>

    @GET("allquestions")
    fun AllQuestion(): Call<MutableList<Question>>

    @GET("getuserEmail/{email}")
    fun getuserbyemail( @Path("email") email : String): Call<MutableList<User>>

    @GET("allreponses")
    fun AllReponse(): Call<MutableList<Reponse>>


    @PUT("getuser/{id}")
    fun getByid(@Path("id") id : String): Call<User>

    @POST("createreponse")
    fun addReponse(@Body info: RequestBody): Call<Reponse>
    companion object {

        var BASE_URL = "http://192.168.1.5:3000/"

        fun create() : ApiInterface {
            val httpClient = OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.MINUTES)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}