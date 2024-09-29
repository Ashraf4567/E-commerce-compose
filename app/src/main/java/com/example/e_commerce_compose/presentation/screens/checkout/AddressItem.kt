package com.example.e_commerce_compose.presentation.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.ui.theme.EcommerceComposeTheme
import com.example.e_commerce_compose.ui.theme.PrimaryText

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: Address,
    isSelected: Boolean = false,
    showDeleteIcon: Boolean = false,
    onSelectClick: () -> Unit,
    onDeleteClicked: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
    ){
        Row {
            Image(
                painter = painterResource(id = R.drawable.baseline_location_pin_24),
                contentDescription = "Location",
                modifier = Modifier
                    .size(35.dp)
            )
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = address.name ?: "",
                    color = PrimaryText,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                Text(text = address.city + ", " + address.details + "\n Phone: " + address.phone)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

        if (showDeleteIcon) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .clickable { onDeleteClicked(address.id ?: "") }
            )
        }else{
            RadioButton(
                selected = isSelected,
                onClick = {onSelectClick()},
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .height(1.dp)
                .align(Alignment.BottomCenter)
                .background(Color.LightGray)
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