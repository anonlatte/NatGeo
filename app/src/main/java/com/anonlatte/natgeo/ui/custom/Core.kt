package com.anonlatte.natgeo.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.anonlatte.natgeo.R

@ExperimentalCoilApi
@Composable
fun CoilImage(modifier: Modifier, data: Any?) {
    val painter = rememberImagePainter(
        data = data,
        builder = { placeholder(R.drawable.whales) }
    )
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        if (painter.state is ImagePainter.State.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (painter.state is ImagePainter.State.Error) {
            Image(
                modifier = modifier.fillMaxSize(),
                painter = painterResource(R.drawable.placeholder_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}
