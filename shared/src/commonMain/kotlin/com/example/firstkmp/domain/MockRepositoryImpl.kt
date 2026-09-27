package com.example.firstkmp.domain

import com.example.firstkmp.data.LayerItem
import com.example.firstkmp.data.news.LocalResult
import com.example.firstkmp.data.news.OrganicResult
import com.example.firstkmp.data.news.RelatedSearche
import com.example.firstkmp.data.news.SerpStack
import kotlinx.coroutines.delay

class MockRepositoryImpl : LayerRepository {

    private val mockData = listOf(
        LayerItem(
            alpha2Code = "US",
            alpha3Code = "USA",
            altSpellings = listOf("United States", "USA"),
            callingCodes = listOf("1"),
            capital = "Washington, D.C.",
            name = "United States of America",
            region = "Americas",
            topLevelDomain = listOf(".us")
        ),
        LayerItem(
            alpha2Code = "CA",
            alpha3Code = "CAN",
            altSpellings = listOf("Canada"),
            callingCodes = listOf("1"),
            capital = "Ottawa",
            name = "Canada",
            region = "Americas",
            topLevelDomain = listOf(".ca")
        ),
        LayerItem(
            alpha2Code = "GB",
            alpha3Code = "GBR",
            altSpellings = listOf("United Kingdom", "Great Britain", "England"),
            callingCodes = listOf("44"),
            capital = "London",
            name = "United Kingdom of Great Britain and Northern Ireland",
            region = "Europe",
            topLevelDomain = listOf(".uk")
        ),
        LayerItem(
            alpha2Code = "DE",
            alpha3Code = "DEU",
            altSpellings = listOf("Germany"),
            callingCodes = listOf("49"),
            capital = "Berlin",
            name = "Germany",
            region = "Europe",
            topLevelDomain = listOf(".de")
        )
    )


    override suspend fun getLayers(): NetworkResult<List<LayerItem>> {
        delay(1000)
        return if (mockData.isEmpty()){
            NetworkResult.Error("No Data")
        } else{
            NetworkResult.Success(mockData)
        }
    }

    override suspend fun getLayersBySearch(query: String): NetworkResult<List<LayerItem>> {
       delay(500)
        if (query.isNotBlank() && mockData.isEmpty()){
            return NetworkResult.Error("No Data")
        }
        else {
            val filtered = mockData.filter { it.name.contains(query, ignoreCase = true) }
            return NetworkResult.Success(filtered)
        }
    }

    override suspend fun getSerpSearch(query: String): NetworkResult<List<RelatedSearche>> {
        TODO("Not yet implemented")
    }

}