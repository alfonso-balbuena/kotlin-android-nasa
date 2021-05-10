package com.udacity.asteroidradar.webservice

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.model.NeowsResponse
import com.udacity.asteroidradar.model.PictureOfDay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface INasaService {

    @GET("neo/rest/v1/feed")
    suspend fun getNearObjects(
        @Query("api_key") api_key: String, @Query("start_date") start_date: String, @Query(
            "end_date"
        ) end_date: String
    ) : NeowsResponse

    @GET("planetary/apod")
    suspend fun getImageDay(@Query("api_key") api_key: String) : PictureOfDay


}

class NasaService {
    companion object {
        fun create() : INasaService {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val retrofit : Retrofit
            if(BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                retrofit = Retrofit.Builder().baseUrl("https://api.nasa.gov/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(client)
                    .build()
            } else {
                retrofit = Retrofit.Builder().baseUrl("https://api.nasa.gov/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
            }
            return retrofit.create(INasaService::class.java)
        }
    }
}