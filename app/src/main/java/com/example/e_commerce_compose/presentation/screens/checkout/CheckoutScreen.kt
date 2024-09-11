package com.example.e_commerce_compose.presentation.screens.checkout

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.presentation.components.LabeledOutlinedTextFiled
import com.example.e_commerce_compose.presentation.screens.productDetails.ProductDetailsTopBar
import com.example.e_commerce_compose.ui.theme.PrimaryText

val paymentMethods = listOf("Cash on delivery", "Credit Card", "PayPal")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel,
    onNavigateToSuccess: (totalPrice: Double, paymentMethod: String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect){
                is CheckoutEffects.NavigateToSuccess -> onNavigateToSuccess(effect.totalPrice , effect.paymentMethod)
                is CheckoutEffects.ShowToast -> {
                    Toast.makeText(context , effect.message , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            ProductDetailsTopBar(
                onBackClicked = {},
                title = "Checkout"
            )
        }
    ) {paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)){
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                        vertical = paddingValues.calculateTopPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.showAddressBottomSheet) {
                    AddAddressBottomSheet(state, viewModel)
                }
                if (state.userAddresses.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.userAddresses.size){
                            AddressItem(
                                address = state.userAddresses[it],
                                isSelected = state.selectedAddress?.id == state.userAddresses[it].id,
                                onSelectClick = {
                                    viewModel.onEvent(
                                        CheckoutEvents.SetSelectedAddress(state.userAddresses[it])
                                    )
                                }
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        viewModel.onEvent(CheckoutEvents.UpdateAddressBottomSheetState(true))
                    },
                    modifier = Modifier.align(Alignment.End),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryText.copy(alpha = 0.8f)
                    )
                ) {
                    Text(text = "Add address")
                }
                paymentMethods.forEach { option->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = state.selectedPaymentOption == option,
                            onClick = { /*TODO*/ }
                        )
                        Text(text = option)
                    }
                }

            }

            Button(
                onClick = {
                    viewModel.onEvent(CheckoutEvents.ConfirmOrderClicked)
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = "Confirm Order")
            }
            if (state.mainLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddAddressBottomSheet(
    state: CheckoutState,
    viewModel: CheckoutViewModel
) {
    val scrollState = rememberScrollState()
    // i need to configure sheet state to make it open in max height
    val sheetState  = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.onEvent(CheckoutEvents.UpdateAddressBottomSheetState(false))
        },
        modifier = Modifier.fillMaxHeight(0.9f),
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "Add your address")
            LabeledOutlinedTextFiled(
                value = state.address.name ?: "",
                label = "Address name",
                placeholder = "Home",
                keyboardType = KeyboardType.Text,
                shape = RoundedCornerShape(6.dp)
            ) {
                viewModel.onEvent(
                    CheckoutEvents.SetAddressName(it)
                )
            }
            LabeledOutlinedTextFiled(
                value = state.address.details ?: "",
                label = "Address details",
                placeholder = "123 Street, Cairo, Egypt",
                keyboardType = KeyboardType.Text,
                shape = RoundedCornerShape(6.dp),
                minLines = 3
            ) {
                viewModel.onEvent(
                    CheckoutEvents.SetAddressDetails(it)
                )
            }
            LabeledOutlinedTextFiled(
                value = state.address.phone ?: "",
                label = "Phone number",
                placeholder = "123456789",
                keyboardType = KeyboardType.Phone,
                shape = RoundedCornerShape(6.dp),
                prefix = "+20"
            ) {
                viewModel.onEvent(
                    CheckoutEvents.SetPhoneNumber(it)
                )
            }
            LabeledOutlinedTextFiled(
                value = state.address.city ?: "",
                label = "City",
                placeholder = "Cairo",
                keyboardType = KeyboardType.Text,
                shape = RoundedCornerShape(6.dp),
                imeAction = ImeAction.Done
            ) {
                viewModel.onEvent(
                    CheckoutEvents.SetCity(it)
                )
            }
            Button(
                onClick = {
                    viewModel.onEvent(CheckoutEvents.SaveAddressClicked)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Save address")
                AnimatedVisibility(visible = state.addNewAddressLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }


}