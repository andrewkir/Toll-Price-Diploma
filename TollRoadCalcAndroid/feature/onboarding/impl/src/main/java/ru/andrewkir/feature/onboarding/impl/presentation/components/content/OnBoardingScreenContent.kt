package ru.andrewkir.feature.onboarding.impl.presentation.components.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import ru.andrewkir.core.presentation.presentation.components.BlackTextButton
import ru.andrewkir.core.presentation.presentation.components.RoundedButton
import ru.andrewkir.core.presentation.presentation.utils.Dimens
import ru.andrewkir.feature.onboarding.impl.presentation.components.OnBoardingPage
import ru.andrewkir.feature.onboarding.impl.presentation.components.PageIndicator
import ru.andrewkir.feature.onboarding.impl.presentation.components.pages
import ru.andrewkir.feature.onboarding.impl.presentation.contract.OnBoardingUIEvent

@OptIn(ExperimentalFoundationApi::class) //rememberPagerState
@Composable
fun OnBoardingScreenContent(
    modifier: Modifier = Modifier,
    onEvent: (OnBoardingUIEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Далее")
                    1 -> listOf("Назад", "Завершить")
                    else -> listOf("", "")
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            OnBoardingPage(
                page = pages[index]
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.MediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(Dimens.PageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            val scope = rememberCoroutineScope()

            Row {
                if (buttonState.value[0].isNotEmpty()) {
                    BlackTextButton(
                        text = buttonState.value[0],
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                }

                RoundedButton(
                    text = buttonState.value[1],
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 1) {
                                onEvent(OnBoardingUIEvent.SaveAppEntry)
                            } else {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardingScreenPreview() {
    OnBoardingScreenContent {

    }
}