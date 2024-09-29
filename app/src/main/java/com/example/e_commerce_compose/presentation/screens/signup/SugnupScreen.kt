package com.example.e_commerce_compose.presentation.screens.signup

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.example.e_commerce_compose.presentation.components.LabeledOutlinedTextFiled
import com.example.e_commerce_compose.presentation.navigation.Graph
import com.example.e_commerce_compose.presentation.navigation.Screens
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignupViewmodel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.effects.collect{effect->
            when(effect){
                SignupEffects.NavigateToHome -> {
                    navController.navigate(Graph.HOME){
                        popUpTo(Screens.SignUp.route){
                            inclusive = true
                        }
                    }
                }
                SignupEffects.NavigateToSignIn -> {
                    navController.navigate(Screens.SignIn.route){
                        popUpTo(Screens.SignUp.route){
                            inclusive = true
                        }
                    }
                }
                is SignupEffects.ShowToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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
    ){
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Fill your information below or register with your social account.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth(.8f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        LabeledOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            label = "Name",
            placeholder = "john doe",
            keyboardType = KeyboardType.Email,
            icon = Icons.Default.AccountCircle,
            isError = state.nameError.isNotEmpty(),
            errorMessage = state.nameError
        ) {
            viewModel.onEvent(SignupEvents.SetName(it))
        }

        LabeledOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            label = "Email",
            placeholder = "exampl@gmail.com",
            keyboardType = KeyboardType.Email,
            icon = Icons.Default.Email,
            isError =  state.emailError.isNotEmpty(),
            errorMessage =  state.emailError
        ) {
            viewModel.onEvent(SignupEvents.SetEmail(it))
        }

        Spacer(modifier = Modifier.height(20.dp))
        LabeledOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            label = "Password",
            placeholder = "********",
            keyboardType = KeyboardType.Password,
            icon = Icons.Default.Lock,
            isError =  state.passwordError.isNotEmpty(),
            errorMessage =  state.passwordError,
        ) {
            viewModel.onEvent(SignupEvents.SetPassword(it))
        }
        LabeledOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = state.rePassword,
            label = "rePassword",
            placeholder = "********",
            keyboardType = KeyboardType.Password,
            icon = Icons.Default.Lock,
            isError =  state.rePasswordError.isNotEmpty(),
            errorMessage =  state.rePasswordError,
        ) {
            viewModel.onEvent(SignupEvents.SetRePassword(it))
        }
        LabeledOutlinedTextFiled(
            modifier = Modifier.fillMaxWidth(),
            value = state.phone,
            label = "Phone",
            placeholder = "example 0123456789",
            keyboardType = KeyboardType.Phone,
            icon = Icons.Default.Phone,
            isError =  state.phoneError.isNotEmpty(),
            errorMessage = state.phoneError,
            imeAction = ImeAction.Done
        ) {
            viewModel.onEvent(SignupEvents.SetPhone(it))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = true,
                onCheckedChange = {}
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "I agree to the Terms & Conditions",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = {
                viewModel.onEvent(SignupEvents.SignUp)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = PrimaryBlue.copy(alpha = 0.8f)
            ),
            enabled = true,
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
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Already have an account?",
            color = PrimaryBlue,
            modifier = Modifier.clickable {
                viewModel.onEvent(SignupEvents.SignInClicked)
            }
        )
    }
}