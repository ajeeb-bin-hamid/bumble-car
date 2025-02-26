package com.ajeeb.bumblecar.common.presentation.ui.text_field.basic

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajeeb.bumblecar.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BumbleCarBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String,
    isError: Boolean,
    filteredSuggestions: List<String>,
    setOnValueChange: (String) -> Unit,
    searchOnValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    val labelSize by animateFloatAsState(
        if (isFocused || value.isNotEmpty()) 12f else 16f, label = ""
    )

    LaunchedEffect(filteredSuggestions) {
        isExpanded = filteredSuggestions.isNotEmpty() && isFocused
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = filteredSuggestions.isNotEmpty(),
        onExpandedChange = { }) {

        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .menuAnchor(MenuAnchorType.PrimaryEditable, true)
            .onFocusChanged { value ->
                isFocused = value.isFocused
            },
            value = value,
            maxLines = 1,
            isError = isError,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = label,
                    fontSize = labelSize.sp,
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            leadingIcon = {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                errorLabelColor = MaterialTheme.colorScheme.secondary,
                errorContainerColor = MaterialTheme.colorScheme.background,
                errorIndicatorColor = MaterialTheme.colorScheme.onError
            ),
            onValueChange = {
                searchOnValueChange(it)
            })

        ExposedDropdownMenu(modifier = Modifier.background(MaterialTheme.colorScheme.background),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }) {
            filteredSuggestions.forEach { suggestion ->
                DropdownMenuItem(text = {
                    Text(
                        suggestion, style = MaterialTheme.typography.bodyMedium
                    )
                }, onClick = {
                    setOnValueChange(suggestion)
                    isExpanded = false
                })
            }
        }
    }
}