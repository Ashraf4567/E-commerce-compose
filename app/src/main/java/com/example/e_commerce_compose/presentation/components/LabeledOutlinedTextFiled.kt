package com.example.e_commerce_compose.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_compose.ui.theme.PrimaryBlue

@Composable
fun LabeledOutlinedTextFiled(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType,
    icon: ImageVector? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {

    Column(modifier = modifier) {
        Text(text = label)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),
            visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            trailingIcon = {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorMessage , color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}