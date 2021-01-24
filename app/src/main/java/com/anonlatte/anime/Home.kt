package com.anonlatte.anime

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.*

private val dummyArticles = listOf(
    Article(
        id = UUID.randomUUID().toString(),
        title = "200,000 miles of Roman roads provided the framework for empire",
        category = "History Magazine",
        photo = "https://www.nationalgeographic.com/content/dam/ngdotcom/rights-exempt/homepage/hero-crops/paved-road-aleppo.adapt.536.1.jpg"
    ),
    Article(
        id = UUID.randomUUID().toString(),
        title = "Whales, myths, and Arctic lakes: Explore Iceland’s new road trip",
        category = "Travel",
        photo = "https://www.nationalgeographic.com/content/dam/travel/2021-digital/arctic-way-iceland/gettyimages-975294558.ngsversion.1611082181931.adapt.1900.1.jpg"
    ),
    Article(
        id = UUID.randomUUID().toString(),
        title = "200,000 miles of Roman roads provided the framework for empire",
        category = "History Magazine",
        photo = "https://www.nationalgeographic.com/content/dam/ngdotcom/rights-exempt/homepage/hero-crops/paved-road-aleppo.adapt.536.1.jpg"
    ),
    Article(
        id = UUID.randomUUID().toString(),
        title = "200,000 miles of Roman roads provided the framework for empire",
        category = "History Magazine",
        photo = "https://www.nationalgeographic.com/content/dam/ngdotcom/rights-exempt/homepage/hero-crops/paved-road-aleppo.adapt.536.1.jpg"
    )
)

@Composable
fun Home() {
    Column(modifier = Modifier.background(Color(0xFFFAFAFA))) {
        News(articles = dummyArticles)
    }
}

@Composable
private fun News(articles: List<Article>) {
    ScrollableColumn(contentPadding = PaddingValues(16.dp)) {
        articles.forEach {
            if (it == articles[0]) {
                ArticleMainItem(title = it.title, category = it.category, photo = it.photo)
            } else {
                ArticleItem(title = it.title, category = it.category, photo = it.photo)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArticleItem() {
    ArticleItem(
        title = dummyArticles[0].title,
        category = dummyArticles[0].category,
        photo = dummyArticles[0].photo
    )
}

@Preview
@Composable
private fun PreviewArticleMainItem() {
    ArticleMainItem(
        title = dummyArticles[1].title,
        category = dummyArticles[1].category,
        photo = dummyArticles[1].photo
    )
}

@Composable
private fun ArticleItem(
    title: String,
    category: String,
    photo: String
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.preferredHeight(312.dp)
        ) {
            Column {
                Image(
                    bitmap = imageResource(id = R.drawable.whales),
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentHeight()
                ) {
                    Text(text = category, style = typography.caption)
                    Text(text = title, style = typography.h5)
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Menu, modifier = Modifier.size(16.dp), tint = Color.Black)
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.preferredSize(16.dp))
    }
}

@Composable
private fun ArticleMainItem(
    title: String,
    category: String,
    photo: String
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.preferredHeight(312.dp)
        ) {
            Box {
                Image(
                    bitmap = imageResource(id = R.drawable.field),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(text = category, style = typography.caption, color = Color.White)
                    Text(text = title, style = typography.h5, color = Color.White)
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Menu, modifier = Modifier.size(16.dp), tint = Color.White)
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = stringResource(id = R.string.btn_read),
                            style = typography.button,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.preferredSize(16.dp))
    }
}

@Composable
private fun WebImage(data: Any) {
    CoilImage(
        contentScale = ContentScale.Crop,
        data = data,
        fadeIn = true,
        modifier = Modifier.preferredSize(280.dp, 128.dp),
        /*loading = {
            Box {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        },
        error = {
            Image(bitmap = imageResource(id = R.drawable.field))
        }*/
    )
}

data class Article(
    val id: String,
    val title: String,
    val category: String,
    val photo: String
)
