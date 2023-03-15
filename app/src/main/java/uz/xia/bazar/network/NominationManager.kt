package uz.xia.bazar.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.xia.bazar.App
import java.util.concurrent.TimeUnit

class NominationManager {

    companion object{
        var INSTAINCE:NominationService?=null
        fun getInstaince():NominationService{
            val httpClient=OkHttpClient.Builder()
                .addInterceptor(ChuckerInterceptor(App.context!!))
                .connectTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build()
            if (INSTAINCE==null){
                val retrofit=Retrofit.Builder()
                    .baseUrl()
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .buiild()
                INSTAINCE=retrofit.create(NominationService::class.java)
            }
            return INSTAINCE!!
        }
    }
}