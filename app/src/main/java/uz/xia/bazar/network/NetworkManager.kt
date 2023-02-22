package uz.xia.bazar.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.xia.bazar.common.BASE_URL
import java.util.concurrent.TimeUnit

class NetworkManager {
    companion object{
        private var INSTAINCE:ApiService?=null
        fun getInstaince(context: Context):ApiService{
            if (INSTAINCE==null) {
                val client=OkHttpClient.Builder()
                    .readTimeout(30,TimeUnit.SECONDS)
                    .connectTimeout(30,TimeUnit.SECONDS)
                    .addInterceptor(ChuckerInterceptor(context))
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .build()
                val retrofit=Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                INSTAINCE=retrofit.create(ApiService::class.java)
            }
            return INSTAINCE!!
        }
    }
}