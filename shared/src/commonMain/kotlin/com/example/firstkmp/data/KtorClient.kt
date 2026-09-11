package com.example.firstkmp.data

import com.example.firstkmp.domain.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {

//    val ktorClient : HttpClient =
//        HttpClient {
//            install(ContentNegotiation){
//                json(Json {
//                    prettyPrint = true
//                    isLenient = true
//                    ignoreUnknownKeys = true
//                })
//            }
//        }

    val ktorClient = HttpClient(
        getPlatformEngine()
    ){
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpCache)
    }

    suspend fun getLayer() : NetworkResult<List<LayerItem>> {
        val url = "https://api.apilayer.net/countrylayer/v2/all?access_key=${APIKEY.API_KEY}"
        return try {
            val response = ktorClient.get(url)
            //return response.body()
            NetworkResult.Success(response.body())


        } catch (e: Exception) {
            //throw e
            NetworkResult.Error(message = e.message ?: "Network Error", exception = e)
        }

        }

    suspend fun getLayerBySearch(countryName : String) : NetworkResult<List<LayerItem>> {

        val url = "https://api.apilayer.net/countrylayer/v2/name/$countryName?access_key=${APIKEY.API_KEY}"

        return try {
            val response = ktorClient.get(url)
            NetworkResult.Success(response.body())
        }catch (e: Exception){
            NetworkResult.Error(message = e.message ?: "Network Error", exception = e)
        }

    }

    }

