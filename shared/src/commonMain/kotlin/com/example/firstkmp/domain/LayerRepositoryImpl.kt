package com.example.firstkmp.domain

import com.example.firstkmp.data.KtorClient
import com.example.firstkmp.data.LayerItem
import com.example.firstkmp.data.news.LocalResult
import com.example.firstkmp.data.news.OrganicResult
import com.example.firstkmp.data.news.RelatedSearche
import com.example.firstkmp.data.news.SerpStack

class LayerRepositoryImpl(private val client: KtorClient) : LayerRepository {
    override suspend fun getLayers(): NetworkResult<List<LayerItem>> {
        return client.getLayer()
    }

    override suspend fun getLayersBySearch(query: String): NetworkResult<List<LayerItem>> {
        return client.getLayerBySearch(query)
    }

    override suspend fun getSerpSearch(query: String): NetworkResult<List<RelatedSearche>> {
        return client.getSerpSearch(query)
    }

}