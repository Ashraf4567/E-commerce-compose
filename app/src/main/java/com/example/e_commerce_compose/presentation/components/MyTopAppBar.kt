package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.ui.theme.PrimaryBlue


@Composable
fun MyTopAppBar(
    modifier: Modifier,
    currentUserName: String = "",
    showGreeting: Boolean = true,
    onQueryChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        if (showGreeting) {
            Text(
                text = "Welcome back, $currentUserName",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontFamily = Font(R.font.greating_font).toFontFamily()
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                hint = "what do you looking for...",
                onTextChange = onQueryChange,
                modifier = Modifier
                    .weight(.9f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "ShoppingCart",
                tint = PrimaryBlue,
                modifier = Modifier
                    .weight(.1f)
                    .size(40.dp)
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String = "",
    onTextChange: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChange(it)
        },
        placeholder = {
            Text(text = hint)
        },
        modifier = modifier,
        prefix = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = PrimaryBlue
            )
        },
        shape = RoundedCornerShape(25.dp),
        suffix = {
            if (text.isNotEmpty()){
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear",
                    tint = PrimaryBlue,
                    modifier = Modifier.clickable {
                        text = ""
                        onTextChange("")
                    }
                )
            }
        }
    )
}