package uz.xia.bazar.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.xia.bazar.App
import uz.xia.bazar.common.BASE_URL
import java.util.concurrent.TimeUnit

class NetworkManager {
    companion object {
        private var INSTAINCE: ApiService? = null
        fun getInstaince(): ApiService {
            if (INSTAINCE == null) {
                val interceptor = ChuckerInterceptor.Builder(App.context!!)
                    .collector(ChuckerCollector(App.context!!))
                    .maxContentLength(250_000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(30L, TimeUnit.SECONDS)
                    .connectTimeout(30L, TimeUnit.SECONDS)
                    .writeTimeout(30L, TimeUnit.SECONDS)
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                INSTAINCE = retrofit.create(ApiService::class.java)
            }
            return INSTAINCE!!
        }
    }
}