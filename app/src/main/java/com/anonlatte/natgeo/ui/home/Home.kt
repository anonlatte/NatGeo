package com.anonlatte.natgeo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anonlatte.natgeo.BuildConfig
import com.anonlatte.natgeo.R
import com.anonlatte.natgeo.data.model.Article
import com.anonlatte.natgeo.ui.custom.SearchField
import com.anonlatte.natgeo.utils.debounce
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.isFinalState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import timber.log.Timber

@Composable
private fun ArticleItem(
    title: String,
    urlToImage: String? = null
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.requiredHeight(Dimension.articleCardHeight)
        ) {
            Column {
                CoilImage(modifier = Modifier.weight(1f), data = urlToImage)
                Column(
                    modifier = Modifier
                        .padding(Dimension.marginNormal)
                        .wrapContentHeight()
                ) {
                    Text(text = title, style = typography.h5)
                    TextButton(onClick = { /*TODO*/ }) {
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

@Composable
private fun CoilImage(
    modifier: Modifier, data: Any?
) {
    val painter = rememberCoilPainter(
        request = data,
        previewPlaceholder = R.drawable.whales
    )
    LaunchedEffect(painter) {
        snapshotFlow { painter.loadState }
            .filter { it.isFinalState() }
            .collect {
                if (it is ImageLoadState.Error && BuildConfig.DEBUG) {
                    Timber.d(it.throwable)
                }
            }
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        when (painter.loadState) {
            is ImageLoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ImageLoadState.Error -> {
                Image(
                    modifier = modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.placeholder_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
private fun ArticleMainItem(
    title: String,
    urlToImage: String? = null
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.requiredHeight(Dimension.articleCardHeight)
        ) {
            Box {
                CoilImage(modifier = Modifier.fillMaxSize(), data = urlToImage)
                Column(
                    modifier = Modifier
                        .padding(Dimension.marginNormal)
                        .align(Alignment.BottomStart)
                ) {
                    Text(text = title, style = typography.h5, color = Color.White)
                    TextButton(onClick = { /*TODO*/ }) {
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
private fun News(articles: List<Article> = emptyList()) {
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
                ArticleMainItem(title = item.title, urlToImage = item.urlToImage)
            } else {
                ArticleItem(title = item.title, urlToImage = item.urlToImage)
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
private fun LoadNews(viewModel: HomeViewModel = viewModel()) {
    val newsUiState: NewsUiState by viewModel.uiState.collectAsState(initial = NewsUiState.Loading)

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
            News((newsUiState as NewsUiState.Success).news)
        }
    }
}

@Preview
@Composable
private fun PreviewArticleItem() {
    ArticleItem(LoremIpsum(10).values.joinToString(" "))
}

@Preview
@Composable
private fun PreviewArticleMainItem() {
    ArticleMainItem(LoremIpsum(5).values.joinToString(" "))
}

@Composable
fun Home(viewModel: HomeViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val queryJob = debounce<String>(500, scope) {
        if (it.isNotBlank()) {
            viewModel.getNews(it)
        }
    }
    Column(modifier = Modifier.background(Color(0xFFF0F0F0))) {
        SearchField(modifier = Modifier.fillMaxWidth(), onValueChange = queryJob)
        LoadNews()
    }
}
