package com.example.e_commerce_compose.presentation.screens.profile.shopping_address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.presentation.components.AddAddressContent
import com.example.e_commerce_compose.presentation.screens.checkout.AddressItem
import com.example.e_commerce_compose.presentation.screens.productDetails.MyTopBar
import com.example.e_commerce_compose.presentation.screens.profile.ProfileViewModel
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressesScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by profileViewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState  = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(key1 = Unit){
        profileViewModel.handleGetCurrentAddress()
    }

    Scaffold(
        topBar = {
            MyTopBar(
                title = "Addresses",
                onBackClicked = { onBackClick() }
            )
        },
        floatingActionButton = {
            IconButton(
                modifier = Modifier
                    .background(
                        PrimaryBlue.copy(alpha = 0.8f),
                        RoundedCornerShape(10.dp)
                    ),
                onClick = {
                    profileViewModel.updateShowAddAddressDialog(true)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else if (state.addresses.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.addresses.size){
                    AddressItem(
                        address = state.addresses[it],
                        onSelectClick = {},
                        onDeleteClicked = {id->
                            profileViewModel.handleDeleteAddress(id)
                        },
                        showDeleteIcon = true
                    )
                }
            }
        }
        if (state.showAddAddressDialog){
            ModalBottomSheet(
                onDismissRequest = {
                profileViewModel.updateShowAddAddressDialog(false)
            }, sheetState = sheetState) {
                AddAddressContent(
                    isLoading = state.isLoading,
                    addressName = state.addressToAdd.name?:"",
                    addressDetails = state.addressToAdd.details?:"",
                    phoneNumber = state.addressToAdd.phone?:"",
                    city = state.addressToAdd.city?:"",
                    onAddressNameChanged = {
                        profileViewModel.setAddressName(it)
                    },
                    onAddressDetailsChanged = {
                        profileViewModel.setAddressDetails(it)
                    },
                    onPhoneNumberChanged = {
                        profileViewModel.setPhoneNumber(it)
                    },
                    onCityChanged = {
                        profileViewModel.setCity(it)
                    },
                    onSaveAddressClicked = {
                        profileViewModel.handleSaveNewAddress(
                            onSuccessful = {
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        profileViewModel.updateShowAddAddressDialog(false)
                                    }
                                }
                            }
                        )

                    }
                )
            }
        }
    }

}