package com.example.e_commerce_compose.presentation.screens.checkout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.presentation.components.AddAddressContent
import com.example.e_commerce_compose.presentation.components.CustomAddAddressButton
import com.example.e_commerce_compose.presentation.screens.productDetails.MyTopBar

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
            MyTopBar(
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
                        item {
                            CustomAddAddressButton(
                                onClick = {
                                    viewModel.onEvent(CheckoutEvents.UpdateAddressBottomSheetState(true))
                                }
                            )
                        }
                    }
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
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "Confirm Order",
                    style = MaterialTheme.typography.titleLarge
                )
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
        AddAddressContent(
            isLoading = state.addNewAddressLoading,
            addressName = state.address.name ?: "",
            addressDetails = state.address.details ?: "",
            phoneNumber = state.address.phone ?: "",
            city = state.address.city ?: "",
            onAddressNameChanged = { viewModel.onEvent(CheckoutEvents.SetAddressName(it)) },
            onAddressDetailsChanged = { viewModel.onEvent(CheckoutEvents.SetAddressDetails(it)) },
            onPhoneNumberChanged = { viewModel.onEvent(CheckoutEvents.SetPhoneNumber(it)) },
            onCityChanged = { viewModel.onEvent(CheckoutEvents.SetCity(it)) },
            onSaveAddressClicked = { viewModel.onEvent(CheckoutEvents.SaveAddressClicked) }
        )
    }


}