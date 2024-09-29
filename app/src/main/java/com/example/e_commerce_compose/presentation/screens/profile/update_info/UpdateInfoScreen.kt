package com.example.e_commerce_compose.presentation.screens.profile.update_info

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.presentation.components.LabeledOutlinedTextFiled
import com.example.e_commerce_compose.presentation.screens.signup.SignupEvents
import com.example.e_commerce_compose.ui.theme.PrimaryBlue
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun UpdateInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateInfoViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.effects.collect{effect->
            when(effect){
                is UpdateInfoEffects.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Update Info")
                },
                navigationIcon = {
                    IconButton(onClick = {onBackClick()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {padding->
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabeledOutlinedTextFiled(
                label = "Name",
                value = state.name,
                placeholder = "",
                isError = false,
                icon = null,
                errorMessage = "",
            ) {
                viewModel.onEvent(UpdateInfoEvents.SetName(it))
            }

            LabeledOutlinedTextFiled(
                label = "Email",
                value = state.email,
                placeholder = "",
                isError = false,
                icon = null,
                errorMessage = "",
            ) {
                viewModel.onEvent(UpdateInfoEvents.SetEmail(it))
            }

            LabeledOutlinedTextFiled(
                label = "Phone",
                value = state.phone,
                placeholder = "",
                isError = false,
                icon = null,
                errorMessage = "",
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ) {
                viewModel.onEvent(UpdateInfoEvents.SetPhone(it))
            }

            Button(
                onClick = {
                    viewModel.onEvent(UpdateInfoEvents.UpdateInfoClicked)
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
                    text = "Update & Save",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                AnimatedVisibility(visible = false) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(start = 4.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}