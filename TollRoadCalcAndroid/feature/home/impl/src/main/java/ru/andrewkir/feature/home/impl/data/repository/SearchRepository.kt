package ru.andrewkir.feature.home.impl.data.repository

import ru.andrewkir.feature.home.impl.data.api.SearchApi

class SearchRepository(
    private val api: SearchApi,
) {

    suspend fun searchLocation(query: String) =
        api.searchLocation(
            query = query,
            fields = SEARCH_PARAMS,
            locale = LOCALE_RU
        )


    companion object {

        private const val LOCALE_RU =
            "ru_RU"
        private const val SEARCH_PARAMS =
            "items.point,items.address,adm_div,adm_div.city,adm_div.region,adm_div.district"
    }
}