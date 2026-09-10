package com.example.firstkmp.domain

import com.example.firstkmp.data.KtorClient
import com.example.firstkmp.data.LayerItem

class LayerRepositoryImpl(private val client: KtorClient) : LayerRepository {
    override suspend fun getLayers(): List<LayerItem> {
        return client.getLayer()
    }

    override suspend fun getLayersBySearch(query: String): List<LayerItem> {
        return client.getLayerBySearch(query)
    }


}