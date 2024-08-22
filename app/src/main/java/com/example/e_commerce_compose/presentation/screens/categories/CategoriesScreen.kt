package com.example.e_commerce_compose.presentation.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.presentation.components.MyTopAppBar

@Composable
fun CategoriesScreen(
    state: CategoriesState,
    onEvent: (CategoriesEvents) -> Unit
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                modifier = Modifier.padding(top = 25.dp),
                showGreeting = false,
                onQueryChange = {}
            )
        }
    ) {
        if (state.categoriesLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator()
            }
        } else {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = it.calculateTopPadding() + 8.dp, bottom = 8.dp)
                    .padding(horizontal = 4.dp)
                    .fillMaxSize(),
            ) {
                CategoriesList(
                    modifier = Modifier.weight(.3f),
                    onItemClick = onEvent,
                    state = state
                )

                SubCategoriesList(
                    modifier = Modifier.weight(.7f),
                    state = state
                )


            }
        }

    }
}

