package com.example.e_commerce_compose.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.presentation.components.MyTopAppBar
import com.example.e_commerce_compose.presentation.navigation.Graph
import com.example.e_commerce_compose.presentation.navigation.Screens
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect{result->
            when(result){
                HomeEffects.NavigateToCart -> {
                    navController.navigate(Graph.CHECKOUT)
                }

                is HomeEffects.NavigateToBrowseProducts -> {
                    navController.navigate(Screens.BrowseProducts.createRoute(result.categoryId))
                }

                is HomeEffects.NavigateToProductDetails -> {
                    navController.navigate(Screens.ProductDetails.createRoute(result.productId))
                }
            }
        }
    }
    var topBarHeight by remember {
        mutableStateOf(120.dp)
    }

    val scrollState = rememberScrollState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                val newTopBarHeight = topBarHeight + delta.dp
                topBarHeight = newTopBarHeight.coerceIn(
                    minimumValue = 0.dp,
                    maximumValue = 120.dp
                )
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(HomeEvents.LoadData)

    }
    Scaffold(
        topBar = {
            MyTopAppBar(
                modifier = Modifier
                    .height(topBarHeight)
                    .padding(top = 25.dp) ,
                onQueryChange = {},
                currentUserName = state.currentUserName,
                onNavigateToCart = {
                    viewModel.onEvent(HomeEvents.NavigateToCart)
                }
            )
        }
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center

            ) {
                CircularProgressIndicator()
            }
        }else{
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
                    onCategoryClicked = {categoryId->
                        viewModel.onEvent(HomeEvents.OnCategoryClicked(categoryId))
                    }
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
                    products = state.productList,
                    onProductClicked = {productId->
                        viewModel.onEvent(HomeEvents.OnProductClicked(productId))
                    }
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
}