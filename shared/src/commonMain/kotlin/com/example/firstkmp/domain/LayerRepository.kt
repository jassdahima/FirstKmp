package com.example.firstkmp.domain

import com.example.firstkmp.data.LayerItem
import com.example.firstkmp.data.news.LocalResult
import com.example.firstkmp.data.news.OrganicResult
import com.example.firstkmp.data.news.RelatedSearche
import com.example.firstkmp.data.news.SerpStack

interface LayerRepository {

    suspend fun getLayers() : NetworkResult<List<LayerItem>>

    suspend fun getLayersBySearch(query : String) : NetworkResult<List<LayerItem>>

    suspend fun getSerpSearch(query : String) : NetworkResult<List<RelatedSearche>>

}
