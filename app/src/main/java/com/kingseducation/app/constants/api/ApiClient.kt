package com.kingseducation.app.constants.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    //    var base_url = "http://ec2-54-236-247-24.compute-1.amazonaws.com"
//    var base_url = "http://gama.mobatia.in:8080/kingseducation/public/"
    var base_url = "https://mobile.kings-edu.com/"

    private lateinit var apiService: ApiService
    fun getApiService(): ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().connectTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS).readTimeout(0, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        apiService = retrofit.create(ApiService::class.java)
        return apiService
    }
}
//http://gama.mobatia.in:8080/kingseducation/public/
/*object ApiClient {
    //    var BASE_URL = "http://bisad.mobatia.in:8081/"
//      var BASE_URL = "https://stagingcms.bisad.ae/"
    var BASE_URL = "https://mobile.bisad.ae/"
    // var BASE_URL ="http://192.168.0.166/bisadv8/"


    val getClient: ApiInterface
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ApiInterface::class.java)

        }

}*/
