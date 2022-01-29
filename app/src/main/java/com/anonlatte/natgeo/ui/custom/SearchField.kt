package com.anonlatte.natgeo.ui.custom

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchHint: String,
    onBackClicked: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    var searchIcon: SearchFieldIcon by remember { mutableStateOf(SearchFieldIcon.Search) }
    var isTrailingIconVisible by remember { mutableStateOf(false) }
    val focusRequester = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchQuery,
        onValueChange = {
            isTrailingIconVisible = it.isNotBlank()
            searchIcon = getRequiredIcon(searchQuery = searchQuery)
            onValueChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(),
        leadingIcon = {
            IconButton(
                enabled = searchIcon is SearchFieldIcon.Back,
                onClick = {
                    focusRequester.clearFocus()
                    keyboardController?.hide()
                    onBackClicked()
                }) {
                Icon(
                    imageVector = searchIcon.value,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        trailingIcon = {
            if (isTrailingIconVisible) {
                IconButton(onClick = {
                    onValueChange("")
                    isTrailingIconVisible = false
                    searchIcon = getRequiredIcon(searchQuery = searchQuery)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )

                }
            }
        },
        singleLine = true,
        modifier = modifier
            .onFocusChanged {
                searchIcon = getRequiredIcon(it.isFocused, searchQuery)
            },
        placeholder = {
            Text(
                text = searchHint,
                color = MaterialTheme.colors.onSecondary
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}

private fun getRequiredIcon(
    hasFocus: Boolean = false,
    searchQuery: String
): SearchFieldIcon = if (hasFocus || searchQuery.isNotBlank()) {
    SearchFieldIcon.Back
} else {
    SearchFieldIcon.Search
}

sealed class SearchFieldIcon(val value: ImageVector) {
    object Back : SearchFieldIcon(Icons.Outlined.ArrowBack)
    object Search : SearchFieldIcon(Icons.Outlined.Search)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSearchField() = SearchField(
    modifier = Modifier.fillMaxWidth(),
    searchQuery = "",
    "",
    {

    }
)