package ru.andrewkir.feature.home.impl.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.andrewkir.core.utils.format
import ru.andrewkir.feature.home.impl.presentation.contract.HomeUIEvent
import ru.andrewkir.feature.home.impl.presentation.model.RouteInfo

@Composable
fun BottomRoutesSelector(
    modifier: Modifier = Modifier,
    routes: List<RouteInfo>,
    onEvent: (HomeUIEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            shape = RoundedCornerShape(
                topEnd = 16.dp, topStart = 16.dp, bottomEnd = 0.dp, bottomStart = 0.dp
            ),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
            ),
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                routes.forEach { route ->
                    item {
                        Column(
                            modifier = Modifier
                                .padding(2.dp)
                                .padding(horizontal = 6.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (route.isSelected) {
                                        MaterialTheme.colorScheme.secondary
                                    } else {
                                        Color.White
                                    }
                                )
                                .clickable(enabled = !route.isSelected, onClick = {
                                    onEvent(HomeUIEvent.OnRouteButtonClicked(route))
                                }),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = if (route.duration > 3600) {
                                    "•${(route.duration / 3600.0).format(1)} ч"
                                } else {
                                    "•${route.duration / 60} мин"
                                }
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(bottom = 4.dp),
                                text = "•${(route.distance / 1000.0).format(1)} км"
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .border(1.dp, Color.Black, shape = RoundedCornerShape(6.dp))
                                    .padding(vertical = 2.dp, horizontal = 4.dp),
                                text = if (route.cost == null) "*" else "${route.cost} руб"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BottomRoutesSelectorPreview() {
    BottomRoutesSelector(
        modifier = Modifier.fillMaxWidth(),
        routes = listOf(
            RouteInfo(
                cost = 10,
                duration = 5000,
                distance = 125,
                isSelected = true,
                points = listOf(),
                tollRoads = listOf(),
            ),
            RouteInfo(
                cost = 10,
                duration = 60,
                distance = 1252,
                isSelected = false,
                points = listOf(),
                tollRoads = listOf(),
            )
        ),
        onEvent = {},
    )
}