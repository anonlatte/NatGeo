package com.anonlatte.natgeo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anonlatte.natgeo.R
import com.anonlatte.natgeo.data.model.Article
import com.anonlatte.natgeo.utils.Constant.dummyArticles
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
private fun ArticleItem(
    title: String,
    urlToImage: String? = null
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.requiredHeight(dimensionResource(R.dimen.articleCardHeight))
        ) {
            Column {
                if (urlToImage != null) {
                    CoilImage(
                        data = urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) { CircularProgressIndicator() }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.marginNormal))
                        .wrapContentHeight()
                ) {
                    Text(text = title, style = typography.h5)
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(dimensionResource(R.dimen.marginNormal)),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.marginExtraSmall)))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.requiredSize(dimensionResource(R.dimen.marginNormal)))
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
            modifier = Modifier.requiredHeight(dimensionResource(R.dimen.articleCardHeight))
        ) {
            Box {
                if (urlToImage != null) {
                    CoilImage(
                        data = urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) { CircularProgressIndicator() }
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.marginNormal))
                        .align(Alignment.BottomStart)
                ) {
                    Text(text = title, style = typography.h5, color = Color.White)
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(dimensionResource(R.dimen.marginNormal)),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.marginExtraSmall)))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.requiredSize(dimensionResource(R.dimen.marginNormal)))
    }
}

@Composable
private fun News(articles: List<Article> = emptyList()) {
    if (articles.isEmpty()) {
        return
    }
    LazyColumn(
        modifier = Modifier.padding(
            PaddingValues(
                horizontal = dimensionResource(R.dimen.marginNormal),
                vertical = dimensionResource(R.dimen.marginSmall)
            )
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.marginExtraSmall))
    ) {
        item {
            ArticleMainItem(
                title = articles.first().title,
                urlToImage = articles.first().urlToImage
            )
        }
        if (articles.size > 1) {
            for (i in 1 until articles.size) {
                item {
                    ArticleItem(title = articles[i].title, urlToImage = articles[i].urlToImage)
                }
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
    ArticleItem(title = dummyArticles[0].title)
}

@Preview
@Composable
private fun PreviewArticleMainItem() {
    ArticleMainItem(title = dummyArticles[1].title)
}

@Composable
fun Home() {
    Column(modifier = Modifier.background(Color(0xFFF0F0F0))) {
        LoadNews()
    }
}
