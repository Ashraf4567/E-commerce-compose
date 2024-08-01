package com.example.e_commerce_compose.presentation.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.presentation.components.CategoryItem
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun SubCategoriesList(
    modifier: Modifier,
    state: CategoriesState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.subCategoriesLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.selectedCategory?.image)
                    .crossfade(true)
                    .build()
                ,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 180.dp , height = 210.dp)
            )

            LazyColumn {
                items(state.subCategories?.size?:0){index->
                    SubCategoryItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                        ,
                        subCategoryName = state.subCategories?.get(index)?.name!!
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategoryItem(
    modifier: Modifier,
    subCategoryName: String
) {
    ElevatedCard(
        onClick = { /*TODO*/ },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = subCategoryName,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun CategoriesList(
    modifier: Modifier,
    state: CategoriesState,
    onItemClick: (CategoriesEvents) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            .background(
                PrimaryBlue.copy(alpha = .1f)
            )
        ,
    ) {
        itemsIndexed(state.categories?: emptyList()) { _, item ->
            CategoryItem(
                category = item?: Category(),
                isSelected = state.selectedCategory?.id == item?.id,
                onCategoryClick = {
                    onItemClick(
                        CategoriesEvents.CategoryClicked(it)
                    )
                }
            )
        }
    }
}