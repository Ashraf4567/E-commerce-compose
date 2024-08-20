package com.example.e_commerce_compose.presentation.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.e_commerce_compose.R

@Composable
fun SignInBackground(modifier: Modifier = Modifier) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cart_anim))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 3f
    )
}