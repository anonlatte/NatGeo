package com.anonlatte.natgeo.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.anonlatte.natgeo.R
import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.navigation.NavDestinations
import com.anonlatte.natgeo.ui.custom.CoilImage
import com.anonlatte.natgeo.ui.custom.SearchBar
import com.anonlatte.natgeo.ui.custom.SearchBarState
import com.anonlatte.natgeo.ui.home.state.NewsUiEvent
import com.anonlatte.natgeo.ui.home.state.NewsUiState
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModel
import com.anonlatte.natgeo.ui.theme.Dimension
import com.anonlatte.natgeo.utils.debounce
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ArticleItem(
    data: Article,
    onArticleClick: (Article) -> Unit = {}
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.requiredHeight(Dimension.articleCardHeight),
            onClick = { onArticleClick(data) }
        ) {
            Column {
                CoilImage(modifier = Modifier.weight(1f), data = data.urlToImage)
                Column(
                    modifier = Modifier
                        .padding(Dimension.marginNormal)
                        .wrapContentHeight()
                ) {
                    Text(text = data.title, style = typography.h5)
                    Row {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.marginNormal),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.padding(Dimension.marginExtraSmall))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.requiredSize(Dimension.marginNormal))
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
private fun ArticleMainItem(
    data: Article,
    onArticleClick: (Article) -> Unit = {}
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.requiredHeight(Dimension.articleCardHeight),
            onClick = { onArticleClick(data) }
        ) {
            Box {
                CoilImage(modifier = Modifier.fillMaxSize(), data = data.urlToImage)
                Column(
                    modifier = Modifier
                        .padding(Dimension.marginNormal)
                        .align(Alignment.BottomStart)
                ) {
                    Text(text = data.title, style = typography.h5, color = Color.White)
                    Row {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(Dimension.marginNormal),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.padding(Dimension.marginExtraSmall))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.requiredSize(Dimension.marginNormal))
    }
}

@Composable
private fun News(articles: List<Article> = emptyList(), onArticleClick: (Article) -> Unit) {
    if (articles.isEmpty()) return
    LazyColumn(
        modifier = Modifier.padding(
            PaddingValues(
                horizontal = Dimension.marginNormal,
                vertical = Dimension.marginSmall
            )
        ),
        verticalArrangement = Arrangement.spacedBy(Dimension.marginExtraSmall)
    ) {
        itemsIndexed(articles) { index, item ->
            if (index == 0) {
                ArticleMainItem(
                    data = item,
                    onArticleClick = onArticleClick
                )
            } else {
                ArticleItem(
                    data = item,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}

@Composable
private fun EmptyListScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.content_empty_news),
            style = MaterialTheme.typography.h5,
        )
    }
}

@Composable
private fun LoadNews(newsUiState: NewsUiState, onArticleClick: (Article) -> Unit) {
    when (newsUiState) {
        is NewsUiState.Error -> {
            EmptyListScreen()
        }
        NewsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NewsUiState.Success -> {
            News(articles = newsUiState.news, onArticleClick = onArticleClick)
        }
    }
}

@Preview
@Composable
private fun PreviewArticleItem() {
    ArticleItem(Article())
}

@Preview
@Composable
private fun PreviewArticleMainItem() {
    ArticleMainItem(Article())
}

@Composable
fun Home(viewModel: HomeViewModel, navController: NavHostController) {
    HandleNewsUiEvent(viewModel, navController)
    val newsUiState: NewsUiState by viewModel.uiState.collectAsState(initial = NewsUiState.Loading)
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var swipeRefreshState: SwipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val scope = rememberCoroutineScope()
    val queryJob = debounce<String>(500, scope) {
        if (it.length >= 3) {
            viewModel.getNews(it)
        }
    }

    if (newsUiState is NewsUiState.Error || newsUiState is NewsUiState.Success) {
        swipeRefreshState = SwipeRefreshState(false)
    }

    Scaffold(
        topBar = {
            SearchBar(
                searchQuery = searchQuery,
                title = stringResource(id = R.string.app_name),
                searchHint = stringResource(R.string.hint_search_news),
                searchBarState = SearchBarState.Collapsed,
                onSearchChanged = {
                    searchQuery = it
                    queryJob(searchQuery)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color(0xFFF0F0F0))
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    swipeRefreshState = SwipeRefreshState(true)
                    if (searchQuery.isEmpty()) {
                        viewModel.getTopHeadlines()
                    } else {
                        viewModel.getNews(searchQuery)
                    }
                }
            ) {
                LoadNews(
                    newsUiState = newsUiState
                ) { article ->
                    viewModel.storeArticleAndNavigate(article)
                }
            }
        }
    }
}

@Composable
fun HandleNewsUiEvent(viewModel: HomeViewModel, navController: NavHostController) {
    val newsUiEvent: NewsUiEvent by viewModel.uiEvent.collectAsState(NewsUiEvent.Idle)
    when (newsUiEvent) {
        is NewsUiEvent.ArticleSaved -> {
            val articleId = (newsUiEvent as NewsUiEvent.ArticleSaved).articleId
            navController.navigate("${NavDestinations.ARTICLE}/${articleId}")
            viewModel.resetEventState()
        }
        NewsUiEvent.ArticleSavingError -> {
            val context = LocalContext.current
            Toast.makeText(
                context,
                stringResource(id = R.string.error_while_navigation),
                Toast.LENGTH_LONG
            ).show()
        }
        NewsUiEvent.Idle -> Unit
    }
}
