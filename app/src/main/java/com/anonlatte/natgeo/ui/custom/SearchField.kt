package com.anonlatte.natgeo.ui.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.anonlatte.natgeo.R

@Composable
fun SearchField(onValueChange: (String) -> Unit) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var searchIcon: ImageVector by remember { mutableStateOf(Icons.Outlined.Search) }
    var isTrailingIconVisible by remember { mutableStateOf(false) }

    TextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            isTrailingIconVisible = it.isNotBlank()
            searchIcon = getRequiredIcon(searchQuery = searchQuery)
            onValueChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        leadingIcon = { Icon(imageVector = searchIcon, contentDescription = null) },
        trailingIcon = {
            if (isTrailingIconVisible) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        searchQuery = ""
                        isTrailingIconVisible = false
                        searchIcon = getRequiredIcon(searchQuery = searchQuery)
                    })
            }
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { searchIcon = getRequiredIcon(it, searchQuery) },
        placeholder = { Text(text = stringResource(id = R.string.hint_search_news)) }
    )
}

private fun getRequiredIcon(
    focusState: FocusState = FocusState.Inactive,
    searchQuery: String
): ImageVector {
    return if (focusState == FocusState.Active || searchQuery.isNotBlank()) {
        Icons.Outlined.ArrowBack
    } else {
        Icons.Outlined.Search
    }
}
