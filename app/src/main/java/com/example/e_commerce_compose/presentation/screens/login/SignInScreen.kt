package com.example.e_commerce_compose.presentation.screens.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_commerce_compose.presentation.components.LabeledOutlinedTextFiled
import com.example.e_commerce_compose.presentation.navigation.Graph
import com.example.e_commerce_compose.presentation.navigation.Screens
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    navController: NavHostController,
) {

    val isKeyboardOpen by keyboardAsState()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect {effect ->
            when (effect) {
                is SignInEffects.NavigateToHome -> {
                    navController.navigate(Graph.HOME){
                        popUpTo(Screens.SignIn.route){
                            inclusive = true
                        }
                    }
                }
                is SignInEffects.ShowToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                SignInEffects.NavigateToForgotPassword -> {}
                SignInEffects.NavigateToSignUp -> {
                    navController.navigate(Screens.SignUp.route){
                        popUpTo(Screens.SignIn.route){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 20.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = !isKeyboardOpen) {
            SignInBackground(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Text(
            text = "Sign In",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Hi! Welcome Back, you've been missed",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(20.dp))
        LabeledOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            label = "Email",
            placeholder = "exampl@gmail.com",
            keyboardType = KeyboardType.Email,
            icon = Icons.Default.Email,
            isError = state.emailError.isNotBlank(),
            errorMessage = state.emailError
        ) {
            viewModel.onEvent(SignInEvents.SetEmail(it))
        }

        Spacer(modifier = Modifier.height(20.dp))
        LabeledOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            label = "Password",
            placeholder = "********",
            keyboardType = KeyboardType.Password,
            icon = Icons.Default.Lock,
            isError = state.passwordError.isNotBlank(),
            errorMessage = state.passwordError,
            imeAction = ImeAction.Done
        ) {
            viewModel.onEvent(SignInEvents.SetPassword(it))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryBlue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    viewModel.onEvent(SignInEvents.ForgotPassword(state.email))
                }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.onEvent(SignInEvents.SignIn)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = PrimaryBlue.copy(alpha = 0.8f)
            ),
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            AnimatedVisibility(visible = state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Sign Up",
                color = PrimaryBlue,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    viewModel.onEvent(SignInEvents.SignUp)
                }
            )
        }

    }


}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}