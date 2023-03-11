package uz.xia.bazar.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.xia.bazar.data.Category
import uz.xia.bazar.data.LoginRequest
import uz.xia.bazar.data.Restaurant
import uz.xia.bazar.data.SmsConform

interface ApiService {

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<Restaurant>>


    @POST("login-by-phone")
    suspend fun getLogin(@Body loginRequest: LoginRequest): Response<Any>

    @POST("conform/sms")
    suspend fun getConformSms(@Body loginRequest: SmsConform): Response<Any>
}