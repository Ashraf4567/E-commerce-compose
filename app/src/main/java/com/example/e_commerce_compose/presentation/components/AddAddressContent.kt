package com.example.e_commerce_compose.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun AddAddressContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    addressName: String,
    addressDetails: String,
    phoneNumber: String,
    city: String,
    onAddressNameChanged: (String) -> Unit,
    onAddressDetailsChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onSaveAddressClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Add your address")
        LabeledOutlinedTextFiled(
            value = addressName,
            label = "Address name",
            placeholder = "Home",
            keyboardType = KeyboardType.Text,
            shape = RoundedCornerShape(6.dp)
        ) {
            onAddressNameChanged(it)
        }
        LabeledOutlinedTextFiled(
            value = addressDetails,
            label = "Address details",
            placeholder = "123 Street, Cairo, Egypt",
            keyboardType = KeyboardType.Text,
            shape = RoundedCornerShape(6.dp),
            minLines = 3
        ) {
            onAddressDetailsChanged(it)
        }
        LabeledOutlinedTextFiled(
            value = phoneNumber,
            label = "Phone number",
            placeholder = "123456789",
            keyboardType = KeyboardType.Phone,
            shape = RoundedCornerShape(6.dp),
            prefix = "+20"
        ) {
            onPhoneNumberChanged(it)
        }
        LabeledOutlinedTextFiled(
            value = city,
            label = "City",
            placeholder = "Cairo",
            keyboardType = KeyboardType.Text,
            shape = RoundedCornerShape(6.dp),
            imeAction = ImeAction.Done
        ) {
            onCityChanged(it)
        }
        Button(
            onClick = {
                onSaveAddressClicked()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Save address")
            AnimatedVisibility(visible = isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}