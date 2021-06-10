package com.anonlatte.natgeo.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Article(
    val source: SourceArticle = SourceArticle(),
    val author: String? = null,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String? = null,
    val publishedAt: String = "",
    val content: String? = null
) : Parcelable

