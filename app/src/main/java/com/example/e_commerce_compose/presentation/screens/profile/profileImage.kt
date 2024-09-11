package com.example.e_commerce_compose.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.R

@Preview(showBackground = true)
@Composable
fun profileImage(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(150.dp)
    ){
        Image(
            painter = painterResource(R.drawable.ic_user),
            contentDescription = "Profile Image"
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Icon",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.BottomEnd)
                .clickable { onEditClick() }
        )
    }
}