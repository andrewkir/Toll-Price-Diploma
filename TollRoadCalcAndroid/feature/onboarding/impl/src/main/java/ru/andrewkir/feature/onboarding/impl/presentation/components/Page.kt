package ru.andrewkir.feature.onboarding.impl.presentation.components

import androidx.annotation.DrawableRes
import ru.andrewkir.feature.onboarding.impl.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val imageResId: Int,
    val links: List<String>? = null,
)

val pages = listOf(
    Page(
        title = "Универсальный калькулятор расчета стоимости проезда по платным дорогам РФ",
        description = """Дипломный проект студентов ВШЭ:
Бондаренко А. М.
Кирюхин А. А.
Лебедев А. В.
Поляков Л. В.

Выполнен в интересах ООО "РНД-42"
""",
        imageResId = R.drawable.toll_price_onboarding,
        links = listOf("rnd-42.com", "cs.hse.ru")
    ),
    Page(
        title = "Гибкий подсчет цены",
        description = "Стройте любые маршруты, а мы посчитаем стоимость проезда, учитывая все необходимые факторы",
        imageResId = R.drawable.road_icon
    )
)
