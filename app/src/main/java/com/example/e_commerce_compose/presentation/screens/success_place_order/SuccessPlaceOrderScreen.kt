package com.example.e_commerce_compose.presentation.screens.success_place_order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.ui.theme.poppins

@Preview(showBackground = true)
@Composable
fun SuccessPlaceOrderScreen(
    modifier: Modifier = Modifier,
    totalPrice: Double? = null,
    paymentMethod: String? = null,
    onNavigateToOrderHistory: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {


    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success_place_order_anim))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LottieAnimation(
            modifier = modifier
                .size(250.dp),
            composition = composition,
            speed = 3f
        )
        //text then say a message an beside it clickable text to go to home screen
        OrderPlaceText {  }
        ReceiptItem(name = "Total Amount", value = "${totalPrice?:0.0} EGP")
        ReceiptItem(name = "Delivery Charge", value = "50 EGP")
        ReceiptItem(name = "Payment Method", value = paymentMethod?:"Cash on delivery")
        Text(
            text =  "Get Delivery by Mon, 12th Aug - Thu, 15th Aug",
            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = poppins , color = Color.Gray , fontSize = 12.sp)
        )
        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = onNavigateToHome,
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Continue Shopping",
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = poppins)
            )
        }
        
    }
}

@Composable
fun ReceiptItem(modifier: Modifier = Modifier , name: String , value: String) {
    Column (
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            )
            Text(text = value)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(3.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun OrderPlaceText(modifier: Modifier = Modifier , onNavigateToOrderHistory: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(tag = "" , annotation = "")
        withStyle(style = SpanStyle(
            color = Color.Gray,
            fontSize = 15.sp
        )) {
            append("Order Placed Successfully. ")
        }
        pop()
        pushStringAnnotation(tag = "OrderHistory", annotation = "OrderHistory")
        withStyle(style = SpanStyle(
            color = Color.Blue,
            fontSize = 15.sp
        )) {
            append("Order History")
        }
        pop()
    }
    ClickableText(
        text = annotatedText,
    ) {offset ->
        annotatedText.getStringAnnotations(tag = "OrderHistory", start = offset, end = offset)
            .firstOrNull()?.let {
                onNavigateToOrderHistory()
            }
    }
}