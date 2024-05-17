package ru.andrewkir.feature.home.impl.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.andrewkir.feature.home.impl.R

@Composable
internal fun AddressRow(
    modifier: Modifier,
    address: String,
    addressOrCoordinates: String,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            modifier = Modifier
                .size(16.dp)
                .padding(top = 4.dp),
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = "test"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {

            Text(
                text = address,
            )

            Text(
                text = addressOrCoordinates,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun AddressSectionPreview() =
    AddressRow(
        modifier = Modifier.fillMaxWidth(),
        address = "Кулакова",
        addressOrCoordinates = "Москва",
    )

