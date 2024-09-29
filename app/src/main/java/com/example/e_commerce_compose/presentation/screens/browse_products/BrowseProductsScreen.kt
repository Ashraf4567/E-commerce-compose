package com.example.e_commerce_compose.presentation.screens.browse_products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerce_compose.presentation.components.HomeProductsItem
import com.example.e_commerce_compose.presentation.components.ShimmerListItem
import com.example.e_commerce_compose.presentation.navigation.Screens
import com.example.e_commerce_compose.presentation.screens.productDetails.MyTopBar
import com.example.e_commerce_compose.ui.theme.poppins
import org.koin.androidx.compose.koinViewModel

val sortOptions = listOf("price" , "sold", "rate")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseProductsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BrowseProductsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is BrowseProductsEffects.NavigateToProductDetails -> {
                    navController.navigate(Screens.ProductDetails.createRoute(effect.productId))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            MyTopBar(
                onBackClicked = navController::popBackStack,
                title = state.screenTitle,
                scrollBehavior = scrollBehavior,
                showCartIcon = false,
                onFilterClicked = {
                    viewModel.onEvent(BrowseProductsEvents.UpdateShowFilters(true))
                }
            )
        }
    ) {paddingValues ->
        if (state.isLoading){
            GridLoadingScreen(paddingValues)
        }else if (state.products.isNotEmpty()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.products.size) { index ->
                    HomeProductsItem(
                        product = state.products[index],
                        contentScale = ContentScale.Fit
                    ) {
                        viewModel.onEvent(BrowseProductsEvents.ProductClicked(it))
                    }
                }
            }
        }else if (state.error != null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "something went wrong")
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {
                        viewModel.onEvent(BrowseProductsEvents.FetchProducts)
                    }) {
                        Text(text = "Retry")
                    }
                }
            }
        }else if (state.isEmptyList){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No products found")
            }
        }
        if (state.showFilters){
            FiltersDialog(
                onDismissRequest = {
                    viewModel.onEvent(BrowseProductsEvents.UpdateShowFilters(false))
                },
                state = state,
                onBrandSelected = {
                    viewModel.onEvent(BrowseProductsEvents.UpdateSelectedBrand(it))
                },
                onApplyFilters = {
                    viewModel.onEvent(BrowseProductsEvents.OnApplyFilters(
                        categoryId = viewModel.categoryId,
                        brandId = state.selectedBrandId,
                        minPrice = null,
                        sort = state.selectedSort
                    ))
                    viewModel.onEvent(BrowseProductsEvents.UpdateShowFilters(false))
                },
                onSortSelected = {
                    viewModel.onEvent(BrowseProductsEvents.UpdateSelectedSort(it))
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersDialog(
    modifier: Modifier = Modifier,
    state: BrowseProductsState,
    onDismissRequest: () -> Unit,
    onApplyFilters: () -> Unit,
    onBrandSelected: (brandId: String) -> Unit,
    onSortSelected: (sort: String) -> Unit
) {
    // i need to configure sheet state to make it open in max height
    val sheetState  = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // this reference to change height of brands grid when it is expanded or not
//    var isBrandGridExpanded by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxHeight(0.9f)
        ,
        sheetState = sheetState
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            Text(
                text = "Filters",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Close , contentDescription = null)
            }
        }
        if (state.fetchBrandsLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator()
            }
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        //need to make this column scrollable
//                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 8.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .padding(horizontal = 8.dp)
                        ,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        header {
                            Text(
                                "Brands",
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        items(state.brands.size) {
                            BrandItem(
                                brand = state.brands[it],
                                isSelected = state.selectedBrandId == state.brands[it].id,
                                onClick = { brandId ->
                                    onBrandSelected(brandId)
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        sortOptions.forEach { sortOption->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = state.selectedSort == sortOption,
                                    onCheckedChange = {
                                        if(it){
                                            onSortSelected(sortOption)
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = sortOption , style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                }
                Button(
                    onClick = onApplyFilters,
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .padding(8.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(text = "Apply Filters")
                }
            }



        }

    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
fun GridLoadingScreen(
    paddingValues: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
            items(20){
                ShimmerListItem()
            }
    }
}


