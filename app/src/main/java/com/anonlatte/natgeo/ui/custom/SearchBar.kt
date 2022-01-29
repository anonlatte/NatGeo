package com.anonlatte.natgeo.ui.custom

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.anonlatte.natgeo.R

@Composable
fun SearchBar(
    searchQuery: String,
    title: String,
    searchHint: String,
    searchIcon: ImageVector = Icons.Filled.Search,
    searchBarState: SearchBarState,
    onSearchChanged: (String) -> Unit,
    onNavigateBack: (() -> Unit)? = null
) {
    var rememberedSearchState by remember { mutableStateOf(searchBarState) }
    val navIcon = getNavIcon(rememberedSearchState, onNavigateBack)
    val titleContent = getTitleContent(rememberedSearchState, title)
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    TopAppBar(
        title = titleContent,
        navigationIcon = navIcon,
        actions = {
            when (rememberedSearchState) {
                SearchBarState.Collapsed -> {
                    IconButton(
                        onClick = { rememberedSearchState = SearchBarState.Expanded }
                    ) {
                        Icon(
                            imageVector = searchIcon,
                            contentDescription = stringResource(id = R.string.hint_search_news)
                        )
                    }
                }
                SearchBarState.Expanded -> {
                    SearchField(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequester),
                        searchQuery = searchQuery,
                        searchHint = searchHint,
                        onBackClicked = { rememberedSearchState = SearchBarState.Collapsed },
                        onValueChange = onSearchChanged
                    )
                }
            }
        }
    )
}

private fun getNavIcon(
    searchBarState: SearchBarState,
    onNavigateBack: (() -> Unit)?
): @Composable (() -> Unit)? {
    return if (searchBarState is SearchBarState.Collapsed && onNavigateBack != null) {
        {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    modifier = Modifier,
                    contentDescription = stringResource(id = R.string.btn_back)
                )
            }
        }
    } else {
        null
    }
}

private fun getTitleContent(
    searchBarState: SearchBarState,
    title: String,
): @Composable () -> Unit {
    return when (searchBarState) {
        SearchBarState.Collapsed -> {
            { Text(title) }
        }
        SearchBarState.Expanded -> {
            {}
        }
    }
}

sealed interface SearchBarState {
    object Collapsed : SearchBarState
    object Expanded : SearchBarState
}

@Preview
@Composable
fun SearchBarCollapsedWithoutIconPreview() {
    SearchBar(
        searchQuery = "",
        title = stringResource(id = R.string.app_name),
        searchHint = stringResource(id = R.string.hint_search_news),
        searchBarState = SearchBarState.Collapsed,
        onSearchChanged = {}
    )
}

@Preview
@Composable
fun SearchBarCollapsedPreview() {
    SearchBar(
        "",
        title = stringResource(id = R.string.app_name),
        searchHint = stringResource(id = R.string.hint_search_news),
        searchBarState = SearchBarState.Collapsed,
        onSearchChanged = {}
    ) {}
}

@Preview
@Composable
fun SearchBarExpandedPreview() {
    SearchBar(
        "",
        title = stringResource(id = R.string.app_name),
        searchHint = stringResource(id = R.string.hint_search_news),
        searchBarState = SearchBarState.Expanded,
        onSearchChanged = {}
    ) {}
}