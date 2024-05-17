package ru.andrewkir.feature.home.impl.domain.interactor

import ru.andrewkir.feature.home.impl.data.repository.MapRepository
import ru.andrewkir.feature.home.impl.data.repository.SearchRepository
import ru.andrewkir.feature.home.impl.domain.models.Point
import ru.andrewkir.feature.home.impl.domain.models.RouteRequest

class SearchInteractor(private val repository: SearchRepository) {

    suspend fun searchLocation(query: String) =
        repository.searchLocation(query)
}