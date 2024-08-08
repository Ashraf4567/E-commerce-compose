package com.example.e_commerce_compose.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.domain.model.Category
import com.example.e_commerce_compose.presentation.components.HomeCategoryItem

@Composable
fun CategoriesGrid(categoryList: List<Category?>?) {

    LazyHorizontalGrid(
        modifier = Modifier.height(380.dp),
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(categoryList?.size ?: 0) {
            categoryList?.get(it)?.let { it1 -> HomeCategoryItem(
                category = it1,
                modifier = Modifier.padding(10.dp)
            ) }
        }

    }
}