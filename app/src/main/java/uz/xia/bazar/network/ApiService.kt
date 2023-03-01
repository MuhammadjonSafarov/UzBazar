package uz.xia.bazar.network

import retrofit2.Response
import retrofit2.http.GET
import uz.xia.bazar.data.Category
import uz.xia.bazar.data.Restaurant

interface ApiService {

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<Restaurant>>
}