package com.example.firstkmp.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.Cache
import java.io.File


actual fun getPlatformEngine(): HttpClientEngine = OkHttp.create {
    config {
        val cacheDirectory = File(System.getProperty("java.io.tmpdir"),"http_cache")
        val cacheSize = 10L * 1024L * 1024L
        cache(Cache(cacheDirectory,cacheSize))

        addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)


            if (response.cacheResponse != null && response.networkResponse == null){
                println("Response from cache")
            }else if (
                response.networkResponse != null
            ){
                println("Response from network")
            }
            response
        }
    }
}