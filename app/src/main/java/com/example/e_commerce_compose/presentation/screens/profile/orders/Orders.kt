package com.example.e_commerce_compose.presentation.screens.profile.orders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.data.model.order.UserOrdersResponseItem
import com.example.e_commerce_compose.presentation.screens.profile.ProfileViewModel
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Orders(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = koinViewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    LaunchedEffect(true) {
        profileViewModel.getOrders()
    }
    val state by profileViewModel.state.collectAsState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Orders History")
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue.copy(alpha = 0.1f),
                    navigationIconContentColor = PrimaryBlue,
                ),
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = {}
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {paddingValues->
        if (state.isLoading){
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else if(state.userOrders.isNotEmpty()){
            LazyColumn(
                modifier = modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.userOrders.forEach { (date, orders) ->
                    stickyHeader(key = date) {
                        Text(
                            text = date ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PrimaryBlue)
                                .padding(8.dp),
                            color = Color.White
                        )
                    }
                    items(orders.size){index->
                        OrderItem(
                            orderUserOrdersResponseItem = orders[index],
                            backgroundColor = if(index % 2 == 0) Color.White else Color.LightGray
                        )
                    }
                }

            }
        }
    }



}