package com.example.e_commerce_compose.presentation.screens.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import com.example.e_commerce_compose.ui.theme.PrimaryText

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: Address,
    isSelected: Boolean = false,
    onSelectClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = PrimaryBlue.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
            ,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = address.name ?: "",
                color = PrimaryText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = PrimaryBlue.copy(alpha = 0.1f)
                    )
                    .height(30.dp),
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = address.city?:"")
                Text(text = address.details?:"")
                Text(text = address.phone?:"")
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .clickable{  }
            )

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .clickable {  }
            )

        }
        RadioButton(
            selected = isSelected,
            onClick = {onSelectClick()},
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun AddressItemPrev(modifier: Modifier = Modifier) {
    EcommerceComposeTheme {
        AddressItem(address = Address(
            name = "Home",
            city = "New York",
            details = "123 Main St",
            phone = "555-1234"
        ) , onSelectClick = {})
    }
}