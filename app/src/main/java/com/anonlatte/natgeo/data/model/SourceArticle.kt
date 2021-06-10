package com.anonlatte.natgeo.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SourceArticle(
    val id: String? = null,
    val name: String = ""
) : Parcelable