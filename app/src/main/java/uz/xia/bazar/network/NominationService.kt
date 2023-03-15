package uz.xia.bazar.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import uz.xia.bazar.common.NOMINATION_URL
import java.util.concurrent.TimeUnit

interface NominationService {
    @GET("reverse.php")
    suspend fun loadAddress(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("accept-language") lang: String = "uz",
        @Query("format") format: String = "json"
    ): NominationResponse?

    @Headers("Accept-Language: uz-UZ, uz;q=0.9, uzc;q=0.8, en;q=0.7, *;q=0.5")
    @GET("search?format=json&country=uzbekistan")
    suspend fun searchPlaces(
        @Query("q") query: String, @Query("accept-language") lang: String = "uz"
    ): List<Place>

    companion object {
        var INSTANCE: NominationService? = null
        fun getInstance(): NominationService {
            val httpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()
            if (INSTANCE == null) {
                val retrofit = Retrofit.Builder().baseUrl(NOMINATION_URL).client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                INSTANCE = retrofit.create(NominationService::class.java)
            }
            return INSTANCE!!
        }

    }

}