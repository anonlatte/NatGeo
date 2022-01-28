package com.anonlatte.natgeo.ui.custom

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.anonlatte.natgeo.R

@Composable
fun SearchField(modifier: Modifier = Modifier, searchQuery: String, onValueChange: (String) -> Unit = {}) {
    var searchIcon: ImageVector by remember { mutableStateOf(Icons.Outlined.Search) }
    var isTrailingIconVisible by remember { mutableStateOf(false) }

    TextField(
        value = searchQuery,
        onValueChange = {
            isTrailingIconVisible = it.isNotBlank()
            searchIcon = getRequiredIcon(searchQuery = searchQuery)
            onValueChange(it)
        },
        /*...*/
        colors = TextFieldDefaults.textFieldColors(),
        leadingIcon = {
            Icon(
                imageVector = searchIcon,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
        },
        trailingIcon = {
            if (isTrailingIconVisible) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onValueChange("")
                        isTrailingIconVisible = false
                        searchIcon = getRequiredIcon(searchQuery = searchQuery)
                    },
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        maxLines = 1,
        modifier = modifier.onFocusChanged {
            searchIcon = getRequiredIcon(it.hasFocus, searchQuery)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.hint_search_news),
                color = MaterialTheme.colors.onPrimary
            )
        }
    )
}

private fun getRequiredIcon(
    hasFocus: Boolean = false,
    searchQuery: String
): ImageVector = if (hasFocus || searchQuery.isNotBlank()) {
    Icons.Outlined.ArrowBack
} else {
    Icons.Outlined.Search
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSearchField() = SearchField(modifier = Modifier.fillMaxWidth(), searchQuery = "")