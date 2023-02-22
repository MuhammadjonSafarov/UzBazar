package uz.xia.bazar.network

import retrofit2.Response
import retrofit2.http.GET
import uz.xia.bazar.data.Category

interface ApiService {

    @GET("categories")
    suspend fun getCategories():Response<List<Category>>
}