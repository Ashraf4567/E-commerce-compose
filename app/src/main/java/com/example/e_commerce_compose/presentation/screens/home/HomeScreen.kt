package com.example.e_commerce_compose.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.presentation.components.MyTopAppBar


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onEvent: (HomeEvents) -> Unit
) {

    val scrollState = rememberScrollState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {}
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(HomeEvents.LoadData)

    }
    Scaffold(
        topBar = {
            MyTopAppBar(modifier = Modifier.padding(top = 25.dp) , onQueryChange = {})
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .verticalScroll(scrollState)
                .padding(top = it.calculateTopPadding() + 10.dp)
                .background(Color.White)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Banner()
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Categories", fontWeight = FontWeight.Bold)
                Text(text = "See All", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            CategoriesGrid(
                categoryList = state.categoriesList,
            )
            Text(
                text = "Video Games",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            ProductsHomeList(
                modifier = Modifier,
                products = state.productList
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.offer_banner),
                contentDescription = "offer banner"
                ,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}