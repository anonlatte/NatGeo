package com.anonlatte.natgeo.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.anonlatte.natgeo.R

@ExperimentalCoilApi
@Composable
fun CoilImage(modifier: Modifier, data: Any?) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = data)
            .apply { placeholder(R.drawable.whales) }
            .build()
    )
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                Image(
                    modifier = modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.placeholder_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else -> Unit
        }
    }
}
