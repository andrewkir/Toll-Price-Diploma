package ru.andrewkir.feature.home.impl.domain.models

data class Locations(
    val result: ResponseWrapperDTO?,
) {

    data class ResponseWrapperDTO(
        val items: List<LocationDetails>,
    )
}

data class LocationDetails(
    val address_name: String?,
    val full_name: String?,
    val id: String?,
    val name: String?,
    val point: DGisGeoPoint?,
    val purpose_name: String?,
    val type: String?,
    val building_name: String?,
)

data class DGisGeoPoint(
    val lon: Double,
    val lat: Double,
    val type: String?,
    val start: Boolean?,
)