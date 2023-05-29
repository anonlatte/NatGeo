package com.anonlatte.natgeo.ui.article

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.ui.article.state.ArticleUiState
import com.anonlatte.natgeo.ui.article.viewmodel.ArticleIntent
import com.anonlatte.natgeo.ui.article.viewmodel.ArticleViewModelImpl
import com.anonlatte.natgeo.ui.custom.CoilImage
import com.anonlatte.natgeo.ui.theme.Dimension

const val ARGS_ARTICLE_ID = "articleId"

@Composable
fun ArticleScreen(
    viewModel: ArticleViewModelImpl,
    navController: NavHostController,
    articleId: Int
) {
    val state by viewModel.uiState.collectAsState(ArticleUiState.Loading)
    Scaffold(
        topBar = {
            TopAppBar({}, navigationIcon = {
                IconButton(onClick = navController::popBackStack) {
                    Icon(Icons.Outlined.ArrowBack, null)
                }
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            when (state) {
                ArticleUiState.Loading -> {
                    // TODO()
                }

                is ArticleUiState.Success -> {
                    ArticleContent((state as ArticleUiState.Success).article)
                }

                is ArticleUiState.Error -> {
                    // TODO()
                }
            }
        }
    }
    viewModel.sendIntent(ArticleIntent.GetArticle(articleId))
}

@Composable
fun ArticleContent(data: Article) {
    Column {
        Box {
            CoilImage(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .requiredHeight((LocalConfiguration.current.screenHeightDp * 0.4).dp),
                data = data.urlToImage
            )
            Text(
                data.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
                    .padding(top = 128.dp)
                    .padding(horizontal = Dimension.marginNormal)
                    .padding(bottom = 32.dp),
                style = typography.h5,
                color = Color.White
            )
        }
        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .padding(vertical = Dimension.marginNormal, horizontal = Dimension.marginNormal)
        ) {
            Text(
                text = data.content ?: data.description,
                style = typography.body1,
            )
        }
    }
}

@Preview
@Composable
fun PreviewArticleContent() {
    ArticleContent(
        Article(
            title = "Test title",
            description = LoremIpsum().values.joinToString(" ")
        )
    )
}
