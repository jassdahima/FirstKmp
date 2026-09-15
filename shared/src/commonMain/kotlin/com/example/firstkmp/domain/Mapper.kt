package com.example.firstkmp.domain

import com.example.firstkmp.data.LayerItem

fun LayerItem.toDomain() : Layers{
    return Layers(
        name = this.name,
        region = this.region,
        alpha2Code = this.alpha2Code,
        alpha3Code = this.alpha3Code,
        altSpellings = this.altSpellings,
        callingCodes = this.callingCodes,
        capital = this.capital,
        topLevelDomain = this.topLevelDomain

    )
}