package com.example.firstkmp.domain

import com.example.firstkmp.data.LayerItem

interface LayerRepository {

    suspend fun getLayers() : List<LayerItem>

    suspend fun getLayersBySearch(query : String) : List<LayerItem>

}
