package com.anonlatte.natgeo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.anonlatte.natgeo.R
import com.anonlatte.natgeo.data.model.Article
import com.anonlatte.natgeo.utils.Constant.dummyArticles
import com.anonlatte.natgeo.utils.DefaultSize
import com.anonlatte.natgeo.utils.Margin

@Composable
private fun ArticleItem(
    title: String,
    urlToImage: String?
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.requiredHeight(DefaultSize.articleCardHeight)
        ) {
            Column {
                Image(
                    painter = painterResource(R.drawable.whales),
                    contentDescription = null,
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(Margin.normal)
                        .wrapContentHeight()
                ) {
                    Text(text = title, style = typography.h5)
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(Margin.normal),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.padding(Margin.extraSmall))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.requiredSize(Margin.normal))
    }
}

@Composable
private fun ArticleMainItem(
    title: String,
    urlToImage: String?
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.requiredHeight(DefaultSize.articleCardHeight)
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.field),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(Margin.normal)
                        .align(Alignment.BottomStart)
                ) {
                    Text(text = title, style = typography.h5, color = Color.White)
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(Margin.normal),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.padding(Margin.extraSmall))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.requiredSize(Margin.normal))
    }
}

@Composable
private fun News(articles: List<Article>) {
    LazyColumn(Modifier.padding(PaddingValues(Margin.normal))) {
        item {
            ArticleMainItem(
                title = articles.first().title,
                urlToImage = articles.first().urlToImage
            )
        }
        for (i in articles) {
            item {
                ArticleItem(title = i.title, urlToImage = i.urlToImage)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArticleItem() {
    ArticleItem(
        title = dummyArticles[0].title,
        urlToImage = dummyArticles[0].urlToImage
    )
}

@Preview
@Composable
private fun PreviewArticleMainItem() {
    ArticleMainItem(
        title = dummyArticles[1].title,
        urlToImage = dummyArticles[1].urlToImage
    )
}

@Composable
fun Home() {
    Column(modifier = Modifier.background(Color(0xFFFAFAFA))) {
        News(articles = dummyArticles)
    }
}
