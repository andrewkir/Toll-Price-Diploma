package ru.andrewkir.feature.onboarding.impl.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.andrewkir.core.presentation.presentation.utils.Dimens.MediumPadding
import ru.andrewkir.feature.onboarding.impl.R

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {

    val uriHandler = LocalUriHandler.current
    val state = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state),
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            painter = painterResource(id = page.imageResId),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )

        Spacer(modifier = Modifier.height(MediumPadding))

        Text(
            text = page.title,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 24.sp,
        )

        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = androidx.appcompat.R.color.material_blue_grey_800),
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(MediumPadding))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            page.links?.forEachIndexed { index, link ->
                val annotatedString = buildAnnotatedString {
                    pushStringAnnotation(tag = "url", annotation = link)
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(link)
                    }
                    pop()
                }
                ClickableText(
                    text = annotatedString,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 18.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(tag = "url", start = offset, end = offset).firstOrNull()?.let {
                            uriHandler.openUri(it.item)
                        }
                    })

                if (page.links.size > 1 && index != page.links.lastIndex) {
                    Text(text = "/")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    OnBoardingPage(
        Modifier.fillMaxSize(),
        Page(
            "Title",
            "Description",
            R.drawable.road_icon,
            links = listOf("google.com", "cs.hse.ru")
        ),
    )
}