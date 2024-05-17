package ru.andrewkir.feature.home.impl.domain.models

enum class TransportClass(
    val title: String,
    val number: Int,
) {
    First(
        title = "Легковой транспорт",
        number = 1,
    ),

    Second(
        title = "Среднегабаритный транспорт",
        number = 2,
    ),

    Third(
        title = "Грузовые и пассажирские ТС",
        number = 3,
    ),

    Fourth(
        title = "Крупногабаритные ТС и автобусы",
        number = 4,
    ),
}